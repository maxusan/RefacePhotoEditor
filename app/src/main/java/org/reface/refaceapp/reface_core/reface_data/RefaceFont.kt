package org.reface.refaceapp.reface_core.reface_data

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.reface.refaceapp.BR
import java.util.*

data class RefaceFont(
    val idReface: String = UUID.randomUUID().toString(),
    val fileNameReface: String = "",
    val nameReface: String = ""
): BaseObservable(){

    

    @Bindable
    var isSelectedReface: Boolean = false
    fun setSelectedReface(){
        isSelectedReface = !isSelectedReface
        
        notifyPropertyChanged(BR.isSelectedReface)
    }
}
