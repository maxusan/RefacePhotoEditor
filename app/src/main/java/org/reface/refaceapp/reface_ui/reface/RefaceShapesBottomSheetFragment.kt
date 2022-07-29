package org.reface.refaceapp.reface_ui.reface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentShapesBottomSheetBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceShape


class RefaceShapesBottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModelReface: RefaceViewModel by activityViewModels()
    private lateinit var bindingReface: FragmentShapesBottomSheetBinding
    private val adapterReface: RefaceShapesAdapter by lazy { RefaceShapesAdapter(viewModelReface) }

    override fun onCreate(savedInstanceStateReface: Bundle?) {
        super.onCreate(savedInstanceStateReface)
        
        setStyle(STYLE_NORMAL, R.style.AppShapeStyle)
    }

    

    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = DataBindingUtil.inflate(inflaterReface, R.layout.fragment_shapes_bottom_sheet, containerReface, false)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewRefacce: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewRefacce, savedInstanceStateReface)
        bindingReface.shapesList.adapter = adapterReface
        adapterReface.submitList(RefaceShape.getShapesListReface())
        bindingReface.refaceCancel.setOnClickListener {
            dismiss()
        }
        
        viewModelReface.refaceShapeClickReface.observe(viewLifecycleOwner){
            val currentListReface = adapterReface.currentList.toMutableList()
            currentListReface.find { shapeReface -> shapeReface.shapeSelectedReface }?.shapeSelectedReface = false
            currentListReface.find { shapeReface -> it.idReface == shapeReface.idReface }?.shapeSelectedReface = true
            adapterReface.submitList(currentListReface)
        }
    }

}