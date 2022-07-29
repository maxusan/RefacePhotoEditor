package org.reface.refaceapp.reface_ui.reface_editor.reface_draw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import me.jfenn.colorpickerdialog.dialogs.ColorPickerDialog
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentDrawBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceColor
import org.reface.refaceapp.reface_core.reface_data.RefaceColorProvider
import java.util.*


class RefaceDrawFragment : Fragment() {

    private lateinit var bindingReface: FragmentDrawBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()
    private val adapterReface: DrawColorsAdapter by lazy { DrawColorsAdapter(viewModelReface) }

    

    override fun onCreateView(
        inflaterRefaec: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentDrawBinding.inflate(inflaterRefaec)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner){
            bindingReface.refaceImage.setImageBitmap(it)
        }
        viewModelReface.getBrushSizeReface().observe(viewLifecycleOwner){
            bindingReface.drawView.sizeReface = it
            bindingReface.size = it
        }
        bindingReface.refaceFirst.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FIRST)
        }
        bindingReface.refaceSecond.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.SECOND)
        }
        bindingReface.refaceThird.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.THIRD)
        }
        bindingReface.refaceFourth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FOURTH)
        }
        bindingReface.fifth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.FIFTH)
        }
        bindingReface.sixth.setOnClickListener {
            viewModelReface.setBrushSizeReface(RefaceBrushSize.SIXTH)
        }
        viewModelReface.refaceColorClickReface.observe(viewLifecycleOwner){
            val currentListReface = adapterReface.currentList.toMutableList()
            val indexOfColorReface = currentListReface.indexOf(it)
            val pastIndexReface = currentListReface.indexOfFirst { colorReface -> colorReface.isSelectedReface }
            currentListReface[pastIndexReface].selectColorReface()
            currentListReface[indexOfColorReface].selectColorReface()
            adapterReface.submitList(currentListReface)
            adapterReface.notifyItemChanged(indexOfColorReface)
            adapterReface.notifyItemChanged(pastIndexReface)
            bindingReface.drawView.color = it.colorReface
        }
        viewModelReface.getDrawModeReface().observe(viewLifecycleOwner){
            bindingReface.refaceState = it
            bindingReface.drawView.modeReface = it
        }
        bindingReface.brush.setOnClickListener {
            viewModelReface.setDrawModeReface(RefaceColorState.COLOR)
        }
        bindingReface.eraser.setOnClickListener {
            viewModelReface.setDrawModeReface(RefaceColorState.RESTORE)
        }
        bindingReface.refaceColorsList.adapter = adapterReface
        bindingReface.refaceCancel.setOnClickListener {
            findNavController().navigateUp()
        }
        bindingReface.refaceDone.setOnClickListener {
            bindingReface.drawView.savingReface = true
            viewModelReface.setImageReface(bindingReface.container.getBitmapReface())
            findNavController().navigateUp()
        }
        viewModelReface.openColorPickerReface.observe(viewLifecycleOwner){
            ColorPickerDialog()
                .withListener { dialogReface, colorpReface ->
                    val currentListReface = adapterReface.currentList.toMutableList()
                    val pastIndexReface = currentListReface.indexOfFirst { colorReface -> colorReface.isSelectedReface }
                    currentListReface[pastIndexReface].isSelectedReface = false
                    val cReface = RefaceColor(
                        idReface = UUID.randomUUID().toString(),
                        colorReface = colorpReface
                    )
                    cReface.isSelectedReface = true
                    currentListReface.add(
                        currentListReface.size - 1,
                        cReface
                    )
                    bindingReface.drawView.color = colorpReface
                    adapterReface.submitList(currentListReface)
                    adapterReface.notifyItemChanged(pastIndexReface)
                }
                .show(requireActivity().supportFragmentManager, "colorPicker")
        }
        adapterReface.submitList(RefaceColorProvider.getColorsReface(requireContext()))
    }
}