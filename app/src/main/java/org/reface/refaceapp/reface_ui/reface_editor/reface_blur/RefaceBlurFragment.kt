package org.reface.refaceapp.reface_ui.reface_editor.reface_blur

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentBlurBinding
import org.reface.refaceapp.reface_ui.reface_editor.reface_draw.RefaceBrushSize

class RefaceBlurFragment : Fragment() {
    private lateinit var bindingRedafe: FragmentBlurBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()

    

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingRedafe = FragmentBlurBinding.inflate(inflaterReface)
        
        return bindingRedafe.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner){
            bindingRedafe.refaceBlurView.setBitmapReface(it!!)
        }
        viewModelReface.getBrushSizeReface().observe(viewLifecycleOwner){
            bindingRedafe.refaceBlurView.sizeReface = it
            bindingRedafe.size = it
        }
        bindingRedafe.refaceFirst.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FIRST)
        }
        bindingRedafe.refaceSecond.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.SECOND)
        }
        bindingRedafe.refaceThird.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.THIRD)
        }
        bindingRedafe.refaceFourth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FOURTH)
        }
        bindingRedafe.fifth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FIFTH)
        }
        bindingRedafe.sixth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.SIXTH)
        }
        viewModelReface.getBlurModeReface().observe(viewLifecycleOwner){
            bindingRedafe.refaceMode = it
            bindingRedafe.refaceBlurView.modeReface = it
        }
        bindingRedafe.refaceBtnPaint.setOnClickListener {
            viewModelReface.setBlurModeReface(RefaceBlurMode.BLUR)
        }
        bindingRedafe.btnClear.setOnClickListener {
            viewModelReface.setBlurModeReface(RefaceBlurMode.RESTORE)
        }
        bindingRedafe.refaceDone.setOnClickListener {
            viewModelReface.setImageReface(bindingRedafe.refaceBlurView.getBitmapReface())
            findNavController().navigateUp()
        }
        bindingRedafe.refaceCancel.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}