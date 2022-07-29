package org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentFontsBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceFontProvider


class RefaceFontsFragment : Fragment() {

    private lateinit var bindingReface: FragmentFontsBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private val adapterReface: FontsAdapter by lazy { FontsAdapter(viewModelReface) }
    
    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentFontsBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        bindingReface.fontsList.adapter = adapterReface
        adapterReface.submitList(RefaceFontProvider.getFontsReface(requireContext()))
        viewModelReface.refaceFontClickReface.observe(viewLifecycleOwner){
            val currentListReface = adapterReface.currentList.toMutableList()
            val indexOfFontReface = currentListReface.indexOf(it)
            val pastIndexReface = currentListReface.indexOfFirst { fontReface -> fontReface.isSelectedReface }
            currentListReface[pastIndexReface].setSelectedReface()
            currentListReface[indexOfFontReface].setSelectedReface()
            adapterReface.submitList(currentListReface)
            adapterReface.notifyItemChanged(indexOfFontReface)
            adapterReface.notifyItemChanged(pastIndexReface)
        }
        
        viewModelReface.currentFontReface.value = adapterReface.currentList.find { fontReface -> fontReface.isSelectedReface }
    }
}