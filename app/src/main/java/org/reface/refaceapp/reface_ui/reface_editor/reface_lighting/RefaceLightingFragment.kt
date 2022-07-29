package org.reface.refaceapp.reface_ui.reface_editor.reface_lighting

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentLightingBinding
import org.reface.refaceapp.reface_ui.reface_editor.OnSeekCustomChangeListener
import org.reface.refaceapp.reface_ui.reface_editor.toPxReface


class RefaceLightingFragment : Fragment() {
    private lateinit var bindingReface: FragmentLightingBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private var brightnessReface = 0f
    private var newBitmapReface: Bitmap? = null
    private var oldBitmapReface: Bitmap? = null


    private val gpuImageViewReface: GPUImageView by lazy { requireView().findViewById(R.id.gpuimageview) }

    

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentLightingBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner) {
            val bitmapReface = Bitmap.createBitmap(it!!.copy(Bitmap.Config.ARGB_8888, true))
            bindingReface.refaceImageview.setImageBmpReface(bitmapReface)
            oldBitmapReface = bitmapReface

            bindingReface.refaceList = listOf(true, false, false, false)

            bindingReface.refaceCancel.setOnClickListener {
                findNavController().popBackStack()
            }

            bindingReface.refaceDone.setOnClickListener {
                viewModelReface.setImageReface(newBitmapReface)
                findNavController().popBackStack()
            }

            bindingReface.seekBar.setOnSeekBarChangeListener(object :
                OnSeekCustomChangeListener() {
                override fun onStopTrackingTouch(p0Reface: SeekBar?) {
                    brightnessReface = p0Reface!!.progress.toFloat()
                    newBitmapReface = enhanceImageReface(
                        bitmapReface!!,
                        1f,
                        brightnessReface
                    )
                    bindingReface.refaceImageview.setImageBmpReface(
                        newBitmapReface
                    )
                }
            })

            bindingReface.recyclerFilters.forEachIndexed { indexReface, viewReface ->
                viewReface.setOnClickListener {
                    oldBitmapReface = newBitmapReface
                    when (indexReface) {
                        0 -> {
                            bindingReface.seekBar.setOnSeekBarChangeListener(object :
                                OnSeekCustomChangeListener() {
                                override fun onStopTrackingTouch(p0Reface: SeekBar?) {
                                    newBitmapReface = enhanceImageReface(
                                        oldBitmapReface ?: bitmapReface!!,
                                        1f,
                                        p0Reface!!.progress.toFloat()
                                    )
                                    bindingReface.refaceImageview.setImageBmpReface(
                                        newBitmapReface
                                    )
                                }
                            })
                            bindingReface.refaceList = listOf(true, false, false, false)
                        }
                        1 -> {
                            bindingReface.refaceList = listOf(false, true, false, false)
                            bindingReface.seekBar.setOnSeekBarChangeListener(object :
                                OnSeekCustomChangeListener() {
                                override fun onStopTrackingTouch(p0Reface: SeekBar?) {
                                    newBitmapReface = enhanceImageReface(
                                        oldBitmapReface ?: bitmapReface!!,
                                        p0Reface!!.progress.toFloat() / 10,
                                        0f
                                    )
                                    bindingReface.refaceImageview.setImageBmpReface(
                                        newBitmapReface
                                    )
                                }
                            })
                        }
                        2 -> {
                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)
                            setLayoutReface()

                            bindingReface.refaceList = listOf(false, false, true, false)
                            switchFilterTo(GPUImageHighlightShadowFilter())
                            refaceFilterAdjusterReface?.adjustReface(50)
                            bindingReface.seekBar.progress = 50

                            setFilterReface(bitmapReface!!)
                        }
                        3 -> {

                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

                            setLayoutReface()
                            switchFilterTo(GPUImageHazeFilter())
                            refaceFilterAdjusterReface?.adjustReface(50)
                            bindingReface.seekBar.progress = 50
                            bindingReface.gpuimageview.visibility = View.GONE

                            setFilterReface(bitmapReface!!)
                            bindingReface.refaceList = listOf(false, false, false, true)
                        }
                    }
                }
            }
        }
    }

    private fun setFilterReface(bitmapReface: Bitmap) {
        bindingReface.seekBar.setOnSeekBarChangeListener(object :
            OnSeekCustomChangeListener() {
            override fun onStopTrackingTouch(p0Reface: SeekBar?) {
                gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)
                refaceFilterAdjusterReface?.adjustReface(p0Reface!!.progress)
                gpuImageViewReface.requestRender()
                gpuImageViewReface.invalidate()
                lifecycleScope.launch(Dispatchers.IO) {
                    newBitmapReface = gpuImageViewReface.capture(
                        bitmapReface.width,
                        bitmapReface.height
                    )
                    bindingReface.refaceImageview.setImageBmpReface(
                        newBitmapReface
                    )
                }
            }
        })
        
        bindingReface.refaceImageview.setImageBmpReface(
            newBitmapReface ?: oldBitmapReface ?: bitmapReface
        )
    }

    private fun setLayoutReface() {
        val lpReface = ConstraintLayout.LayoutParams(
            1,
            1,
        )

        lpReface.bottomToTop = R.id.recycler_filters
        lpReface.topToBottom = R.id.reface_top_bar
        lpReface.startToStart = R.id.reface_ucrop_frame
        lpReface.endToEnd = R.id.reface_ucrop_frame
        lpReface.topMargin = 8
        lpReface.bottomMargin = 105.toPxReface.toInt()
        gpuImageViewReface.layoutParams = lpReface
        
    }

    private var refaceFilterAdjusterReface: RefaceFilterAdjuster? = null

    private fun switchFilterTo(filterReface: GPUImageFilter) {
        if (gpuImageViewReface.filter == null || gpuImageViewReface.filter.javaClass != filterReface.javaClass) {
            gpuImageViewReface.filter = filterReface
            refaceFilterAdjusterReface = RefaceFilterAdjuster(filterReface)
            if (refaceFilterAdjusterReface!!.canAdjustRefaec()) {

            } else {
            }
        }
        
    }

    fun enhanceImageReface(mBitmapReface: Bitmap, contrastReface: Float, brightnessReface: Float): Bitmap? {
        val cmReface = ColorMatrix(
            floatArrayOf(
                contrastReface,
                0f,
                0f,
                0f,
                brightnessReface,
                0f,
                contrastReface,
                0f,
                0f,
                brightnessReface,
                0f,
                0f,
                contrastReface,
                0f,
                brightnessReface,
                0f,
                0f,
                0f,
                1f,
                0f
            )
        )
        val mEnhancedBitmapReface = Bitmap.createBitmap(
            mBitmapReface.width, mBitmapReface.height, mBitmapReface
                .config
        )
        val canvasReface = Canvas(mEnhancedBitmapReface)
        val paintRefaec = Paint()
        paintRefaec.colorFilter = ColorMatrixColorFilter(cmReface)
        canvasReface.drawBitmap(mBitmapReface, 0f, 0f, paintRefaec)
        
        return mEnhancedBitmapReface
    }


}