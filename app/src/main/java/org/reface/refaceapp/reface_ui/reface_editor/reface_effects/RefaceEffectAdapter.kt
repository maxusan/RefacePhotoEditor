package org.reface.refaceapp.reface_ui.reface_editor.reface_effects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.reface.refaceapp.R
import org.reface.refaceapp.databinding.ItemFilterBinding

class EffectAdapter(
    private val clickFilterReface: (refaceFilter: RefaceFilter) -> Unit
) :
    androidx.recyclerview.widget.ListAdapter<RefaceFilter, EffectAdapter.FilterHolderReface>(
        ListCrsMdsCallbackReface()
    ) {
    
    inner class FilterHolderReface(private val bindingReface: ItemFilterBinding) :
        RecyclerView.ViewHolder(bindingReface.root) {
        
        fun bindReface(refaceFilter: RefaceFilter) {
            bindingReface.refaceFilter = refaceFilter
            
            bindingReface.root.setOnClickListener {
                clickFilterReface(refaceFilter)
                val currentReface = currentList
                currentReface.forEach {
                    it.activeReface = false
                }
                currentReface.find {
                    it.titleReface == refaceFilter.titleReface
                }?.activeReface = true
                submitList(
                    currentReface
                )
                refaceFilter.activeReface = true
                bindingReface.refaceFilter = refaceFilter
                notifyDataSetChanged()
            }

            bindingReface.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parentReface: ViewGroup, viewTypeReface: Int): FilterHolderReface {
        val bindingReface: ItemFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parentReface.context),
            R.layout.item_filter,
            parentReface, false
        )
        
        return FilterHolderReface(bindingReface)
    }


    override fun onBindViewHolder(holderReface: FilterHolderReface, positionReface: Int) {
        holderReface.bindReface(currentList[positionReface])
        
    }
}

class ListCrsMdsCallbackReface : DiffUtil.ItemCallback<RefaceFilter>() {
    override fun areItemsTheSame(
        oldItemTexurraReface: RefaceFilter,
        newItemTexurraReface: RefaceFilter
    ): Boolean {
        
        return oldItemTexurraReface.titleReface == newItemTexurraReface.titleReface
    }

    

    override fun areContentsTheSame(
        oldItemTexurraReface: RefaceFilter,
        newItemTexurraReface: RefaceFilter
    ): Boolean {
        
        return oldItemTexurraReface == newItemTexurraReface
    }
}