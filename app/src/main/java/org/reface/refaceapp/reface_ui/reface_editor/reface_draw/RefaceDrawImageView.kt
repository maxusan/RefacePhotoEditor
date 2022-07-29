package org.reface.refaceapp.reface_ui.reface_editor.reface_draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import org.reface.refaceapp.R

class RefaceDrawImageView(contextReface: Context, attributeSetReface: AttributeSet): AppCompatImageView(contextReface, attributeSetReface) {

    var mPaintRefaec: Paint? = null
    var mBitmapReface: Bitmap? = null
    var mCanvasReface: Canvas? = null
    var mPathReface: Path? = null
    var mBitmapPaintReface: Paint? = null
    private var circlePaintReface: Paint? = null
    private var mXReface = 0f
    private var mYReface: Float = 0f
    private val TOUCH_TOLERANCEReface = 4f

    

    var savingReface = false
        set(value) {
            field = value
            
            invalidate()
        }

    var modeReface: RefaceColorState = RefaceColorState.COLOR
        set(value) {
            field = value
            
            invalidate()
        }

    var color: Int = ContextCompat.getColor(contextReface, R.color.reface_color_1)
        set(value) {
            field = value
            mPaintRefaec!!.color = field
            
            mPaintRefaec!!.alpha = 255
            invalidate()
        }

    var sizeReface: RefaceBrushSize = RefaceBrushSize.THIRD
        set(value) {
            field = value
            mPaintRefaec!!.strokeWidth = field.size
            
            invalidate()
        }

    init {
        mPaintRefaec = Paint()
        mPaintRefaec!!.isAntiAlias = true
        mPaintRefaec!!.style = Paint.Style.STROKE
        mPaintRefaec!!.strokeWidth = sizeReface.size
        mPaintRefaec!!.alpha = 255
        mPathReface = Path()
        mBitmapPaintReface = Paint()
        mBitmapPaintReface!!.color = Color.BLACK
        circlePaintReface = Paint()
        circlePaintReface!!.style = Paint.Style.STROKE
        circlePaintReface!!.color = Color.WHITE
        circlePaintReface!!.strokeWidth = 4f
        circlePaintReface!!.alpha = 255
    }

    fun getBitmapRefaec(): Bitmap{
        val bitmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val cvReface = Canvas(bitmapReface)
        cvReface.drawBitmap(mBitmapReface!!, 0f, 0f, null)
        
        return bitmapReface
    }

    override fun onSizeChanged(wReface: Int, hReface: Int, oldwReface: Int, oldhRefaec: Int) {
        super.onSizeChanged(wReface, hReface, oldwReface, oldhRefaec)
        mBitmapReface = Bitmap.createBitmap(wReface, hReface, Bitmap.Config.ARGB_8888)
        mCanvasReface = Canvas(mBitmapReface!!)
        
    }

    override fun draw(canvasReface: Canvas) {
        super.draw(canvasReface)
        if (modeReface == RefaceColorState.RESTORE) {
            makePaintEraseReface()
        }
        else {
            makePaintDrawReface()
        }
        canvasReface.drawBitmap(mBitmapReface!!, 0f, 0f, mBitmapPaintReface)
        canvasReface.drawPath(mPathReface!!, mPaintRefaec!!)
        if(!savingReface){
            canvasReface.drawCircle(mXReface, mYReface, sizeReface.size / 2, circlePaintReface!!)
        }
    }

    private fun makePaintDrawReface() {
        mPaintRefaec = Paint()
        mPaintRefaec!!.isAntiAlias = true
        mPaintRefaec!!.color = color
        mPaintRefaec!!.style = Paint.Style.STROKE
        mPaintRefaec!!.strokeWidth = sizeReface.size
        
        mPaintRefaec!!.alpha = 255
    }

    private fun makePaintEraseReface() {
        mPaintRefaec!!.color = resources.getColor(android.R.color.transparent);
        mPaintRefaec!!.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        
    }


    private fun touchStartReface(xReface: Float, yReface: Float) {
        mPathReface!!.moveTo(xReface, yReface)
        mXReface = xReface
        
        mYReface = yReface
    }

    private fun touchMoveReface(xReface: Float, yReface: Float) {
        val dxReface = Math.abs(xReface - mXReface)
        val dyReface: Float = Math.abs(yReface - mYReface)
        if (dxReface >= TOUCH_TOLERANCEReface || dyReface >= TOUCH_TOLERANCEReface) {
            mPathReface!!.quadTo(mXReface, mYReface, (xReface + mXReface) / 2, (yReface + mYReface) / 2)
            mXReface = xReface
            
            mYReface = yReface
        }
    }

    private fun touchUpReface() {
        mPathReface!!.lineTo(mXReface, mYReface)
        mCanvasReface!!.drawPath(mPathReface!!, mPaintRefaec!!)
        
        mPathReface!!.reset()
    }

    override fun onTouchEvent(eventReface: MotionEvent): Boolean {
        val xReface = eventReface.x
        val yReface = eventReface.y
        when (eventReface.action) {
            MotionEvent.ACTION_DOWN -> {
                touchStartReface(xReface, yReface)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMoveReface(xReface, yReface)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                touchUpReface()
                invalidate()
            }
        }
        
        return true
    }

}