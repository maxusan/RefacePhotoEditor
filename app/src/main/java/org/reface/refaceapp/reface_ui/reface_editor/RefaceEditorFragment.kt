package org.reface.refaceapp.reface_ui.reface_editor

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentEditorBinding


class RefaceEditorFragment : Fragment() {
    private lateinit var bindingReface: FragmentEditorBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    
    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentEditorBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        
        viewModelReface.getImageReface().observe(viewLifecycleOwner) { bitmapReface ->
            bindingReface.refaceImageview.setImageBmpReface(bitmapReface)
            bindingReface.llBottom.forEachIndexed { indexReface, viewReface ->
                viewReface.setOnClickListener {
                    when (indexReface) {
                        0 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToTextFragment()
                        )
                        1 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToDrawFragment()
                        )
                        2 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToEffectFragment()
                        )
                        3 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToOrientationFragment()
                        )
                        4 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToCropFragment()
                        )
                        5 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToLightingFragment()
                        )
                        6 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToColorFragment()
                        )
                        7 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToVignetteFragment()
                        )
                        8 -> findNavController().navigate(
                            RefaceEditorFragmentDirections.actionEditorFragmentToBlurFragment()
                        )
                    }
                }
            }
        }
        bindingReface.refaceCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        bindingReface.refaceDone.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}