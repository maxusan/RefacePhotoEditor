package org.reface.refaceapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reface.refaceapp.reface_core.reface_data.RefaceColor
import org.reface.refaceapp.reface_core.reface_data.RefaceFont
import org.reface.refaceapp.reface_core.reface_data.RefaceShape
import org.reface.refaceapp.reface_ui.reface_editor.reface_blur.RefaceBlurMode
import org.reface.refaceapp.reface_ui.reface_editor.reface_draw.RefaceBrushSize
import org.reface.refaceapp.reface_ui.reface_editor.reface_draw.RefaceColorState
import org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings.RefaceTextSettingsMode

class RefaceViewModel : ViewModel() {

    private val baseBitmapLiveDataReface = MutableLiveData<Bitmap>()
    fun setBaseBitmapReface(bitmapReface: Bitmap) {
        clearBitmapListReface()
        baseBitmapLiveDataReface.postValue(bitmapReface)
        
    }

    

    fun getBaseBitmapReface(): MutableLiveData<Bitmap> {
        
        return baseBitmapLiveDataReface
    }

    private val bitmapsListLiveDataReface = MutableLiveData<MutableList<Bitmap>>(mutableListOf())
    private val currentBitmapIndexReface = MutableLiveData<Int>(-1)
    fun getBitmapIndexReface(): Int {
        
        return currentBitmapIndexReface.value!!
    }
    fun getBitmapIndexLiveReface(): MutableLiveData<Int> {
        
        return currentBitmapIndexReface
    }
    fun addBitmapToListReface(bitmapReface: Bitmap?) {
        if(bitmapReface != null){
            
            bitmapsListLiveDataReface.value!!.add(bitmapReface)
            if (currentBitmapIndexReface.value == bitmapsListLiveDataReface.value!!.size - 1)
                currentBitmapIndexReface.value = currentBitmapIndexReface.value!! + 1
            else
                currentBitmapIndexReface.value = bitmapsListLiveDataReface.value!!.size - 1
        }

    }

    fun getBitmapsListReface(): MutableLiveData<MutableList<Bitmap>> {
        
        return bitmapsListLiveDataReface
    }
    fun clearBitmapListReface() {
        
        bitmapsListLiveDataReface.value = mutableListOf()
        currentBitmapIndexReface.value = -1
    }

    fun redoReface() {
        
        currentBitmapIndexReface.value = currentBitmapIndexReface.value!! + 1
    }

    fun undoReface() {
        
        currentBitmapIndexReface.value = currentBitmapIndexReface.value!! - 1
    }

    private val _shapeClickLiveEventReface = LiveEvent<RefaceShape>()
    val refaceShapeClickReface: LiveData<RefaceShape> = _shapeClickLiveEventReface

    fun shapeClickReface(refaceShape: RefaceShape) {
        
        _shapeClickLiveEventReface.value = refaceShape
    }

    private val brushSizeReface = MutableLiveData(RefaceBrushSize.THIRD)
    fun getBrushSizeReface(): MutableLiveData<RefaceBrushSize> {
        
        return brushSizeReface
    }
    fun setBrushSizeReface(valueReface: RefaceBrushSize) {
        
        brushSizeReface.value = valueReface
    }

    private val _colorClickReface = LiveEvent<RefaceColor>()
    val refaceColorClickReface: LiveData<RefaceColor> = _colorClickReface
    fun colorClickReface(refaceColor: RefaceColor) {
        
        _colorClickReface.value = refaceColor
    }

    private val drawModeReface = MutableLiveData(RefaceColorState.COLOR)
    fun getDrawModeReface(): MutableLiveData<RefaceColorState> {
        
        return drawModeReface
    }
    fun setDrawModeReface(modeReface: RefaceColorState) {
        
        drawModeReface.value = modeReface
    }

    private val blurModeReface = MutableLiveData(RefaceBlurMode.BLUR)
    fun getBlurModeReface(): MutableLiveData<RefaceBlurMode> {
        
        return blurModeReface
    }
    fun setBlurModeReface(modeReface: RefaceBlurMode) {
        
        blurModeReface.value = modeReface
    }

    private val _openColorPickerReface = LiveEvent<Boolean>()
    val openColorPickerReface: LiveData<Boolean> = _openColorPickerReface
    fun openColorPickerReface() {
        
        _openColorPickerReface.value = true
    }

    private val textSettingsModeReface = MutableLiveData(RefaceTextSettingsMode.FONT)
    fun getSettingsModeReface(): MutableLiveData<RefaceTextSettingsMode> {
        
        return textSettingsModeReface
    }
    fun setSettingsModeReface(modeReface: RefaceTextSettingsMode) {
        
        textSettingsModeReface.value = modeReface
    }

    private val _fontClickReface = LiveEvent<RefaceFont>()
    val refaceFontClickReface: LiveData<RefaceFont> = _fontClickReface
    fun fontClickReface(refaceFont: RefaceFont) {
        
        _fontClickReface.value = refaceFont
    }

    private val currentTextReface = MutableLiveData("Some text")
    fun getCurrentTextReface(): MutableLiveData<String> {
        
        return currentTextReface
    }
    fun setCurrentTextReface(textReface: String) {
        
        currentTextReface.value = textReface
    }

    val currentFontReface = MutableLiveData<RefaceFont>(RefaceFont())
    val currentColorReface = MutableLiveData<RefaceColor>(RefaceColor())

    private val imageLiveDataReface = MutableLiveData<Bitmap?>()
    fun setImageReface(bitmapReface: Bitmap?) {
        
        viewModelScope.launch(Dispatchers.IO) {
            if (bitmapReface != null)
                imageLiveDataReface.postValue(bitmapReface!!)
        }
    }

    fun getImageReface(): MutableLiveData<Bitmap?> {
        
        return imageLiveDataReface
    }

}