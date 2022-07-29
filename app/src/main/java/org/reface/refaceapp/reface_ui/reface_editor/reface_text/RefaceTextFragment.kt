package org.reface.refaceapp.reface_ui.reface_editor.reface_text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentTextBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceColorProvider
import org.reface.refaceapp.reface_core.reface_data.RefaceFontProvider
import org.reface.refaceapp.reface_core.reface_data.RefaceTextItem


class RefaceTextFragment : Fragment() {

    private lateinit var bindingReface: FragmentTextBinding
    private val viewModelRefcae: RefaceViewModel by activityViewModels()
    
    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentTextBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelRefcae.getImageReface().observe(viewLifecycleOwner){
            bindingReface.textStickerView.setImageBmpReface(it)
        }
        
        bindingReface.addSticker.setOnClickListener {
            kotlin.runCatching {
                findNavController().navigate(RefaceTextFragmentDirections.actionTextFragmentToTextSettingsFragment())
                bindingReface.textStickerView.addStickerReface(RefaceTextItem(
                    refaceFontReface = viewModelRefcae.currentFontReface.value!!,
                    refaceColorReface = viewModelRefcae.currentColorReface.value!!
                ))
            }
        }
        viewModelRefcae.refaceColorClickReface.observe(viewLifecycleOwner){
            bindingReface.textStickerView.setTextColorReface(it)
        }
        viewModelRefcae.refaceFontClickReface.observe(viewLifecycleOwner){
            bindingReface.textStickerView.setTextTypeFaceReface(it)
        }
        viewModelRefcae.getCurrentTextReface().observe(viewLifecycleOwner){
            bindingReface.textStickerView.setTextReface(it)
        }
        bindingReface.refaceCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        bindingReface.refaceDone.setOnClickListener {
            viewModelRefcae.setImageReface(bindingReface.textStickerView.getBitmapReface())
            findNavController().navigateUp()
        }
        viewModelRefcae.currentFontReface.value = RefaceFontProvider.getFontsReface(requireContext())[0]
        viewModelRefcae.currentColorReface.value = RefaceColorProvider.getColorsReface(requireContext())[0]
    }



}