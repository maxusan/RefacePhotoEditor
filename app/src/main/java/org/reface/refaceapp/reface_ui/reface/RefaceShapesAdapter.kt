package org.reface.refaceapp.reface_ui.reface

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.ListItemShapeBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceShape

class RefaceShapesAdapter(val viewModelReface: RefaceViewModel): ListAdapter<RefaceShape, RefaceShapesAdapter.ShapesHolder>(ShapeCallbackReface()) {
    inner class ShapesHolder(private val bindingReface: ListItemShapeBinding): RecyclerView.ViewHolder(bindingReface.root) {
        init {
            bindingReface.viewModel = viewModelReface
        }
        fun bindShapeRefaee(refaceShape: RefaceShape) {
            
            bindingReface.shape = refaceShape
        }
        
    }

    

    override fun onCreateViewHolder(parentReface: ViewGroup, viewTypeReface: Int): RefaceShapesAdapter.ShapesHolder {
        val bindingReface: ListItemShapeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parentReface.context),
            R.layout.list_item_shape,
            parentReface,
            false
        )
        
        return ShapesHolder(bindingReface)
    }

    override fun onBindViewHolder(holderReface: RefaceShapesAdapter.ShapesHolder, positionReface: Int) {
        holderReface.bindShapeRefaee(currentList[positionReface])
        
    }
}

class ShapeCallbackReface: DiffUtil.ItemCallback<RefaceShape>(){
    override fun areItemsTheSame(oldItemReface: RefaceShape, newItemReface: RefaceShape): Boolean {
        
        return oldItemReface.idReface == newItemReface.idReface
    }
    
    override fun areContentsTheSame(oldItemReface: RefaceShape, newItemReface: RefaceShape): Boolean {
        
        return oldItemReface == newItemReface
    }

}