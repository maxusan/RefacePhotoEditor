package org.reface.refaceapp.reface_ui.reface_editor.reface_color

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
import jp.co.cyberagent.android.gpuimage.filter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentColorBinding
import org.reface.refaceapp.reface_ui.reface_editor.OnSeekCustomChangeListener
import org.reface.refaceapp.reface_ui.reface_editor.reface_lighting.RefaceFilterAdjuster
import org.reface.refaceapp.reface_ui.reface_editor.toPxReface


class RefaceColorFragment : Fragment() {
    private lateinit var bindingReface: FragmentColorBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private var newBitmapReface: Bitmap? = null
    private var oldBitmapReface: Bitmap? = null

    private val gpuImageViewReface: GPUImageView by lazy { requireView().findViewById(R.id.gpuimageview) }

    

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentColorBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner) {
            val bitmapReface = Bitmap.createBitmap(it!!.copy(Bitmap.Config.ARGB_8888, true))
            bindingReface.refaceImageview.setImageBmpReface(bitmapReface)
            oldBitmapReface = bitmapReface
            
            bindingReface.refaceCancel.setOnClickListener {
                findNavController().popBackStack()
            }

            bindingReface.refaceDone.setOnClickListener {
                viewModelReface.setImageReface(newBitmapReface)
                findNavController().popBackStack()
            }

            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

            setLayoutReface()
            switchFilterToReface(GPUImageSaturationFilter())
            refaceFilterAdjuster?.adjustReface(50)
            bindingReface.seekBar.progress = 50
            bindingReface.gpuimageview.visibility = View.GONE

            setFilterReface(bitmapReface!!)
            bindingReface.refaceList = listOf(true, false, false, false)

            bindingReface.recyclerFilters.forEachIndexed { indexReface, viewReface ->
                viewReface.setOnClickListener {
                    oldBitmapReface = newBitmapReface
                    when (indexReface) {
                        0 -> {
                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

                            setLayoutReface()
                            switchFilterToReface(GPUImageSaturationFilter())
                            refaceFilterAdjuster?.adjustReface(50)
                            bindingReface.seekBar.progress = 50
                            bindingReface.gpuimageview.visibility = View.GONE

                            setFilterReface(bitmapReface!!)
                            bindingReface.refaceList = listOf(true, false, false, false)
                        }
                        1 -> {
                            bindingReface.refaceList = listOf(false, true, false, false)
                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

                            setLayoutReface()
                            switchFilterToReface(GPUImageMonochromeFilter())
                            refaceFilterAdjuster?.adjustReface(50)
                            bindingReface.seekBar.progress = 50
                            bindingReface.gpuimageview.visibility = View.GONE

                            setFilterReface(bitmapReface!!)
                        }
                        2 -> {
                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)
                            setLayoutReface()

                            bindingReface.refaceList = listOf(false, false, true, false)
                            switchFilterToReface(GPUImageGammaFilter())
                            refaceFilterAdjuster?.adjustReface(50)
                            bindingReface.seekBar.progress = 50

                            setFilterReface(bitmapReface!!)
                        }
                        3 -> {

                            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

                            setLayoutReface()
                            switchFilterToReface(GPUImageVibranceFilter())
                            refaceFilterAdjuster?.adjustReface(50)
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
                refaceFilterAdjuster?.adjustReface(p0Reface!!.progress)
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

    private var refaceFilterAdjuster: RefaceFilterAdjuster? = null

    private fun switchFilterToReface(filterReface: GPUImageFilter) {
        if (gpuImageViewReface.filter == null || gpuImageViewReface.filter.javaClass != filterReface.javaClass) {
            gpuImageViewReface.filter = filterReface
            refaceFilterAdjuster = RefaceFilterAdjuster(filterReface)
            
            if (refaceFilterAdjuster!!.canAdjustRefaec()) {
//                seekBar.visibility = View.VISIBLE
//                filterAdjuster!!.adjust(seekBar.progress)
            } else {
//                seekBar.visibility = View.GONE
            }
        }
    }
}