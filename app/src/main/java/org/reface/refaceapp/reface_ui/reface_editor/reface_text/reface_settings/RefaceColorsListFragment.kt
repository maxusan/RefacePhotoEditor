package org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentColorsListBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceColor
import org.reface.refaceapp.reface_core.reface_data.RefaceColorProvider
import org.reface.refaceapp.reface_ui.reface_editor.reface_draw.DrawColorsAdapter
import java.util.*


class RefaceColorsListFragment : Fragment() {

    private lateinit var bindingReface: FragmentColorsListBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private val adapterReface: DrawColorsAdapter by lazy { DrawColorsAdapter(viewModelReface) }
    
    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentColorsListBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        bindingReface.colorsList.adapter = adapterReface
        val gridReface = GridLayoutManager(requireContext(), 5)
        bindingReface.colorsList.layoutManager = gridReface
        adapterReface.submitList(RefaceColorProvider.getColorsReface(requireContext()))
        viewModelReface.refaceColorClickReface.observe(viewLifecycleOwner) {
            val currentListReface = adapterReface.currentList.toMutableList()
            val indexOfColorReface = currentListReface.indexOf(it)
            val pastIndexReface = currentListReface.indexOfFirst { colorReface -> colorReface.isSelectedReface }
            currentListReface[pastIndexReface].selectColorReface()
            currentListReface[indexOfColorReface].selectColorReface()
            adapterReface.submitList(currentListReface)
            adapterReface.notifyItemChanged(indexOfColorReface)
            adapterReface.notifyItemChanged(pastIndexReface)
            viewModelReface.currentColorReface.value = it
        }
        viewModelReface.openColorPickerReface.observe(viewLifecycleOwner) {
            ColorPickerDialog()
                .withListener { dialogReface, colorpReface ->
                    val currentListReface = adapterReface.currentList.toMutableList()
                    val cReface = RefaceColor(
                        idReface = UUID.randomUUID().toString(),
                        colorReface = colorpReface
                    )
                    currentListReface.add(
                        currentListReface.size - 1,
                        cReface
                    )
                    adapterReface.submitList(currentListReface)
                }
                .show(requireActivity().supportFragmentManager, "colorPicker")
        }
        viewModelReface.currentColorReface.value = adapterReface.currentList.find { colorReface -> colorReface.isSelectedReface }
    }
}
