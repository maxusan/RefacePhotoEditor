package org.reface.refaceapp.reface_ui.reface_editor.reface_blur

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import jp.co.cyberagent.android.gpuimage.GPUImage
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter
import org.reface.refaceapp.reface_ui.reface_editor.reface_draw.RefaceBrushSize


class RefaceBlurView(contextReface: Context?, attrsReface: AttributeSet?) : View(contextReface, attrsReface) {

    private var bitmapReface: Bitmap? = null
    private var baseBitmapReface: Bitmap? = null
    private var blurredBitmapReface: Bitmap? = null
    private var mXReface = -1f
    private var mYReface = -1f

    private var bmMatrixReface: Matrix = Matrix()
    private var matrixScaledReface: Boolean = false
    private val eraserPaintReface = Paint()

    

    fun setBitmapReface(bitmapReface: Bitmap) {
        this.bitmapReface = bitmapReface
        baseBitmapReface = bitmapReface
        val filterReface = GPUImageGaussianBlurFilter()
        filterReface.setBlurSize(3f)
        val gpuImageReface = GPUImage(context)
        val copyReface = bitmapReface.copy(Bitmap.Config.ARGB_8888, true)
        gpuImageReface.setImage(copyReface)
        gpuImageReface.setFilter(filterReface)
        blurredBitmapReface = gpuImageReface.bitmapWithFilterApplied
        invalidate()
    }

    fun getBmpMatrixReface(): Matrix{
        
        return bmMatrixReface
    }

    fun getBitmapReface(): Bitmap{
        val rtReface = Bitmap.createBitmap(bitmapReface!!.width, bitmapReface!!.height, Bitmap.Config.ARGB_8888)
        val cvReface = Canvas(rtReface)
        cvReface.drawBitmap(bitmapReface!!, Matrix(), null)
        val rt1Reface = Bitmap.createBitmap(bitmapReface!!.width, bitmapReface!!.height, Bitmap.Config.ARGB_8888)
        val cv1Reface = Canvas(rt1Reface)
        cv1Reface.drawBitmap(blurredBitmapReface!!, Matrix(), null)
        cv1Reface.drawBitmap(rtReface, Matrix(), null)
        
        return rt1Reface
    }

    var sizeReface: RefaceBrushSize = RefaceBrushSize.SIXTH
        set(value) {
            field = value
            eraserPaintReface.strokeWidth = field.size
            
            invalidate()
        }

    var modeReface: RefaceBlurMode = RefaceBlurMode.BLUR
        set(value) {
            field = value
            
            invalidate()
        }

    init {
        eraserPaintReface.alpha = 0
        eraserPaintReface.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        eraserPaintReface.isAntiAlias = true
        eraserPaintReface.strokeWidth = sizeReface.size
        eraserPaintReface.maskFilter = BlurMaskFilter(8f, BlurMaskFilter.Blur.NORMAL)
    }

    override fun onTouchEvent(eventReface: MotionEvent): Boolean {
        when (eventReface.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val touchPointsReface = floatArrayOf(eventReface.x, eventReface.y)
                val inverseReface = Matrix()
                bmMatrixReface.invert(inverseReface)
                inverseReface.mapPoints(touchPointsReface)
                if (modeReface == RefaceBlurMode.BLUR) {
                    blurReface(PointF(touchPointsReface[0], touchPointsReface[1]))
                } else {
                    restoreImageReface(PointF(touchPointsReface[0], touchPointsReface[1]))
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mXReface = -1f
                mYReface = -1f
                invalidate()
            }
            else -> {}
        }
        return true
    }

    private fun restoreImageReface(pointFReface: PointF) {
        val copyReface =  baseBitmapReface!!.copy(Bitmap.Config.ARGB_8888, true)
        val bbbReface = getCroppedBitmapReface(
            copyReface,
            pointFReface
        )
        
        val bppReface = Bitmap.createBitmap(bitmapReface!!.copy(Bitmap.Config.ARGB_8888, true))
        val cvReface = Canvas(bppReface)
        cvReface.drawBitmap(bbbReface!!, 0f, 0f, null)
        bitmapReface = bppReface
        invalidate()

    }

    private fun getCroppedBitmapReface(bitmapReface: Bitmap, pointReface: PointF): Bitmap? {
        val outputReface = Bitmap.createBitmap(
            bitmapReface.width,
            bitmapReface.height, Bitmap.Config.ARGB_8888
        )
        val canvasReface = Canvas(outputReface)
        val colorReface = -0xbdbdbe
        val paintRefacce = Paint()
        paintRefacce.strokeWidth = sizeReface.size
        val rectReface = Rect(0, 0, bitmapReface.width, bitmapReface.height)
        paintRefacce.isAntiAlias = true
        paintRefacce.color = colorReface
        canvasReface.drawCircle(
            pointReface.x, pointReface.y,
            sizeReface.size, paintRefacce
        )
        
        paintRefacce.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvasReface.drawBitmap(bitmapReface, rectReface, rectReface, paintRefacce)
        return outputReface
    }

    private fun blurReface(pointFReface: PointF) {
        val copyReface = bitmapReface!!.copy(Bitmap.Config.ARGB_8888, true)
        val canvasReface = Canvas(copyReface)
        canvasReface.drawCircle(pointFReface.x, pointFReface.y, sizeReface.size, eraserPaintReface)
        bitmapReface = copyReface
        
        invalidate()
    }

    override fun onDraw(canvasReface: Canvas) {
        bitmapReface?.let {
            if (!matrixScaledReface) {
                bmMatrixReface.setRectToRect(
                    RectF(0f, 0f, bitmapReface!!.width.toFloat(), bitmapReface!!.height.toFloat()),
                    RectF(0f, 0f, width.toFloat(), height.toFloat()),
                    Matrix.ScaleToFit.CENTER
                )
                matrixScaledReface = true
            }
            canvasReface.drawBitmap(blurredBitmapReface!!, bmMatrixReface, null)
            canvasReface.drawBitmap(it, bmMatrixReface, null)
        }
    }
}