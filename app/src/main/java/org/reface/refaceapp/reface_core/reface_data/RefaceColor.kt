package org.reface.refaceapp.reface_core.reface_data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.reface.refaceapp.BR
import java.io.Serializable
import java.util.*

data class RefaceColor(
    val idReface: String = UUID.randomUUID().toString(),
    val colorReface: Int = 0
): BaseObservable(), Serializable{
    @Bindable
    var isSelectedReface: Boolean = false
    fun selectColorReface() {
        isSelectedReface = !isSelectedReface
        
        notifyPropertyChanged(BR.isSelectedReface)
    }
    
}