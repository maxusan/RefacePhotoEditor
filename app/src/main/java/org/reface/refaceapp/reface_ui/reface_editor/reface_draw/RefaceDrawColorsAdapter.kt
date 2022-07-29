package org.reface.refaceapp.reface_ui.reface_editor.reface_draw

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.ListItemAddColorBinding
import org.reface.refaceapp.databinding.ListItemColorBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceColor

class DrawColorsAdapter(val viewModelReface: RefaceViewModel) :
    ListAdapter<RefaceColor, RecyclerView.ViewHolder>(ColorCallbackReface()) {
    

    inner class ColorHolderReface(val bindingReface: ListItemColorBinding) :
        RecyclerView.ViewHolder(bindingReface.root) {
        init {
            bindingReface.viewModel = viewModelReface
        }

        fun bindColorReface(refaceColor: RefaceColor) {
            bindingReface.refaceColor = refaceColor
            
        }
        
    }
    inner class ColorPickerHolder(val bindingReface: ListItemAddColorBinding): RecyclerView.ViewHolder(bindingReface.root){
        init {
            bindingReface.viewModel = viewModelReface
        }
    }

    override fun onCreateViewHolder(parentReface: ViewGroup, viewTypeReface: Int): RecyclerView.ViewHolder {
        
        when(viewTypeReface){
            0 -> {
                val bindingReface: ListItemColorBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parentReface.context),
                    R.layout.list_item_color,
                    parentReface,
                    false
                )
                return ColorHolderReface(bindingReface)
            }
            else -> {
                val bindingReface: ListItemAddColorBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parentReface.context),
                    R.layout.list_item_add_color,
                    parentReface,
                    false
                )
                return ColorPickerHolder(bindingReface)
            }
        }
    }

    override fun getItemViewType(positionReface: Int): Int {
        
        when(currentList[positionReface].colorReface == 0){
            true -> return 1
            false -> return 0
        }
    }

    override fun onBindViewHolder(holderReface: RecyclerView.ViewHolder, positionReface: Int) {
        
        when(getItemViewType(positionReface)){
            0 ->  (holderReface as ColorHolderReface).bindColorReface(currentList[positionReface])
            else -> {}
        }
    }
}

class ColorCallbackReface : DiffUtil.ItemCallback<RefaceColor>() {
    override fun areItemsTheSame(oldItemReface: RefaceColor, newItemReface: RefaceColor): Boolean {
        
        return oldItemReface.idReface == newItemReface.idReface
    }
    

    override fun areContentsTheSame(oldItemReface: RefaceColor, newItemReface: RefaceColor): Boolean {
        
        return oldItemReface == newItemReface
    }

}