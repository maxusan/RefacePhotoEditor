package org.reface.refaceapp.reface_ui.reface_editor.reface_vignette


import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import jp.co.cyberagent.android.gpuimage.GPUImageView
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentVignetteBinding
import org.reface.refaceapp.reface_ui.reface_editor.OnSeekCustomChangeListener
import org.reface.refaceapp.reface_ui.reface_editor.reface_lighting.RefaceFilterAdjuster
import org.reface.refaceapp.reface_ui.reface_editor.toPxReface


class RefaceVignetteFragment : Fragment() {
    private lateinit var bindingReface: FragmentVignetteBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private var newBitmapReface: Bitmap? = null
    private var oldBitmapReface: Bitmap? = null


    private val gpuImageViewReface: GPUImageView by lazy { requireView().findViewById(R.id.gpuimageview) }
    
    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentVignetteBinding.inflate(inflaterReface)
        
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
                viewModelReface.setImageReface(newBitmapReface ?: oldBitmapReface)
                findNavController().popBackStack()
            }

            gpuImageViewReface.setImage(oldBitmapReface ?: bitmapReface)

            setLayoutReface()
            switchFilterToReface(GPUImageVignetteFilter())
            refaceFilterAdjusterReface?.adjustReface(50)
            bindingReface.seekBar.progress = 50
            bindingReface.gpuimageview.visibility = View.GONE

            setFilterReface(bitmapReface!!)
            bindingReface.refaceList = listOf(true, false, false, false)
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

                newBitmapReface = vignettReface(
                    oldBitmapReface!!,
                    p0Reface!!.progress + 1
                )
                bindingReface.refaceImageview.setImageBmpReface(
                    newBitmapReface
                )
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

    private fun switchFilterToReface(filterReface: GPUImageFilter) {
        if (gpuImageViewReface.filter == null || gpuImageViewReface.filter.javaClass != filterReface.javaClass) {
            gpuImageViewReface.filter = filterReface
            refaceFilterAdjusterReface = RefaceFilterAdjuster(filterReface)
        }
        
    }

    private fun vignettReface(bmReface: Bitmap, pReface: Int): Bitmap? {
        val imageReface = Bitmap.createBitmap(bmReface.width, bmReface.height, Bitmap.Config.ARGB_8888)
        val canvasReface = Canvas(imageReface)
        canvasReface.drawBitmap(bmReface, 0f, 0f, Paint())
        val radReface: Int = if (bmReface.width < bmReface.height) {
            val oReface = bmReface.height * 2 / 100
            bmReface.height - oReface * pReface / 3
        } else {
            val oReface = bmReface.width * 2 / 100
            bmReface.width - oReface * pReface / 3
        }
        val rectReface = Rect(0, 0, bmReface.width, bmReface.height)
        val rectfReface = RectF(rectReface)
        val colorsReface = intArrayOf(0, 0, Color.BLACK)
        val posReface = floatArrayOf(0.0f, 0.1f, 1.0f)
        val linGradLRReface: Shader =
            RadialGradient(
                rectReface.centerX().toFloat(),
                rectReface.centerY().toFloat(),
                radReface.toFloat(),
                colorsReface,
                posReface,
                Shader.TileMode.CLAMP
            )
        val paintReface = Paint()
        paintReface.shader = linGradLRReface
        paintReface.isAntiAlias = true
        paintReface.isDither = true
        paintReface.alpha = 255
        canvasReface.drawRect(rectfReface, paintReface)
        return imageReface
    }
}
