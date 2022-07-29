package org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.ListItemFontBinding
import org.reface.refaceapp.reface_core.reface_data.RefaceFont

class FontsAdapter(val viewModelReface: RefaceViewModel): ListAdapter<RefaceFont, FontsAdapter.FontHolderReface>(FontCallbackReface()) {
    inner class FontHolderReface(val bindingReface: ListItemFontBinding): RecyclerView.ViewHolder(bindingReface.root) {
        
        init {
            bindingReface.viewModel = viewModelReface
        }
        fun bindFontReface(refaceFont: RefaceFont) {
            
            bindingReface.font = refaceFont
        }

    }

    override fun onCreateViewHolder(parentReface: ViewGroup, viewTReface: Int): FontHolderReface {
        val bindingReface: ListItemFontBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parentReface.context),
            R.layout.list_item_font,
            parentReface,
            false
        )
        
        return FontHolderReface(bindingReface)
    }

    override fun onBindViewHolder(holderReface: FontHolderReface, positionReface: Int) {
        holderReface.bindFontReface(currentList[positionReface])
        
    }
    
}

class FontCallbackReface: DiffUtil.ItemCallback<RefaceFont>(){
    override fun areItemsTheSame(oldItemReface: RefaceFont, newItemReface: RefaceFont): Boolean {
        
        return oldItemReface.idReface == newItemReface.idReface
    }
    
    override fun areContentsTheSame(oldItemReface: RefaceFont, newItemReface: RefaceFont): Boolean {
        
        return oldItemReface == newItemReface
    }

}