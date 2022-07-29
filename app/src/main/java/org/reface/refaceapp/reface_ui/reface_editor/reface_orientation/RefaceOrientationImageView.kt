package org.reface.refaceapp.reface_ui.reface_editor.reface_orientation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import org.reface.refaceapp.R


class RefaceOrientationImageView(contextReface: Context, attributeSetReface: AttributeSet) :
    AppCompatImageView(contextReface, attributeSetReface) {

    private var bitmapReface: Bitmap? = null
    private var bitmapMatrixReface: Matrix = Matrix()
    private var matrixScaledReface: Boolean = false

    private var cropRectReface: RectF = RectF()
    private var cropRectPaintReface: Paint = Paint()
    private var cropRectCirclePaintReface: Paint = Paint()
    
    init {
        cropRectPaintReface.color = Color.WHITE
        cropRectPaintReface.strokeWidth = 8f
        cropRectPaintReface.style = Paint.Style.STROKE
        cropRectCirclePaintReface.color = Color.WHITE
        cropRectCirclePaintReface.style = Paint.Style.FILL
        cropRectCirclePaintReface.strokeWidth = 8f
        this.setDrawingCacheEnabled(true);

    }

    fun setImageBmpReface(bitmapReface: Bitmap?) {
        this.bitmapReface = bitmapReface
        matrixScaledReface = false
        
        invalidate()
    }

    var flagSaveReface = false
    var scaleReface = 0f


    fun getImageBmpReface(): Bitmap? {
        flagSaveReface = true
        invalidate()
        if (canvas1Reface != null)
            draw(canvas1Reface)
        try {
            return Bitmap.createBitmap(
                bmapReface!!,
                cropRectReface.left.toInt(),
                cropRectReface.top.toInt(),
                cropRectReface.width().toInt(),
                cropRectReface.height().toInt()
            )
        } catch (eReface: Exception){
            eReface.printStackTrace()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } else {
                bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            canvas1Reface = Canvas(bmapReface!!)
            
            draw(canvas1Reface)
            return getImageBmpReface()
        }
    }

    fun flipBitmapReface(xFlipReface: Boolean, yFlipReface: Boolean): Bitmap? {
        val matrixReface = Matrix()
        matrixReface.postScale(
            if (xFlipReface) -1f else 1f,
            if (yFlipReface) -1f else 1f,
            bitmapReface!!.width / 2f,
            bitmapReface!!.height / 2f
        )
        
        return Bitmap.createBitmap(
            bitmapReface!!,
            0,
            0,
            bitmapReface!!.width,
            bitmapReface!!.height,
            matrixReface,
            true
        )
    }


    fun rotateBitmapReface(degreesReface: Float): Bitmap? {
        val matrixReface = Matrix()
        matrixReface.postRotate(degreesReface)
        
        val scaledBitmapReface =
            Bitmap.createScaledBitmap(bitmapReface!!, bitmapReface!!.width, bitmapReface!!.height, true)
        return Bitmap.createBitmap(
            scaledBitmapReface,
            0,
            0,
            bitmapReface!!.width,
            bitmapReface!!.height,
            matrixReface,
            true
        )
    }

    private var canvas1Reface: Canvas? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onDraw(canvasReface: Canvas) {
        super.onDraw(canvasReface)
        
        bitmapReface?.let {
            if (!matrixScaledReface) {
                if (!flagSaveReface) {
                    bitmapMatrixReface.setRectToRect(
                        RectF(0f, 0f, bitmapReface!!.width.toFloat(), bitmapReface!!.height.toFloat()),
                        RectF(0f, 0f, width.toFloat(), height.toFloat()),
                        Matrix.ScaleToFit.CENTER
                    )
                    val valuesReface = FloatArray(9)
                    bitmapMatrixReface.getValues(valuesReface)

                    cropRectReface.left = valuesReface[Matrix.MTRANS_X] + bitmapReface!!.width / 34
                    cropRectReface.top = valuesReface[Matrix.MTRANS_Y] + bitmapReface!!.height / 34
                    cropRectReface.right = width.toFloat() - valuesReface[Matrix.MTRANS_X] - bitmapReface!!.width / 34
                    cropRectReface.bottom =
                        height.toFloat() - valuesReface[Matrix.MTRANS_Y] - bitmapReface!!.height / 34
                    matrixScaledReface = true
                }
            }

            canvasReface.drawBitmap(it, bitmapMatrixReface, null)

            if (!flagSaveReface) {

                canvasReface.drawRect(cropRectReface, cropRectPaintReface)
                canvasReface.drawLine(
                    cropRectReface.right - cropRectReface.width() / 2,
                    cropRectReface.top,
                    cropRectReface.right - cropRectReface.width() / 2,
                    cropRectReface.bottom,
                    cropRectPaintReface
                )
                canvasReface.drawLine(
                    cropRectReface.left,
                    cropRectReface.bottom - cropRectReface.height() / 2,
                    cropRectReface.right,
                    cropRectReface.bottom - cropRectReface.height() / 2,
                    cropRectPaintReface
                )
                canvasReface.drawCircle(cropRectReface.left + cropRectReface.width()/2, cropRectReface.top, 32f, cropRectCirclePaintReface)
                canvasReface.drawCircle(cropRectReface.left, cropRectReface.bottom - cropRectReface.height()/2, 32f, cropRectCirclePaintReface)
                canvasReface.drawCircle(cropRectReface.right, cropRectReface.top  + cropRectReface.height()/2, 32f, cropRectCirclePaintReface)
                canvasReface.drawCircle(cropRectReface.right- cropRectReface.width()/2, cropRectReface.bottom , 32f, cropRectCirclePaintReface)
                drawBlackBackgorundReface(canvasReface)
            }

            setOnTouchListener { vReface, eReface ->
                when (eReface.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        savedMatrixReface.set(bitmapMatrixReface)
                        startReface[eReface.x] = eReface.y
                        modeReface = DRAGReface
                        lastEventReface = null
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        oldDistReface = spacingReface(eReface)
                        if (oldDistReface > 10f) {
                            savedMatrixReface.set(bitmapMatrixReface)
                            midPointReface(midReface, eReface)
                            modeReface = ZOOReface
                        }
                        lastEventReface = FloatArray(4)
                        lastEventReface!![0] = eReface.getX(0)
                        lastEventReface!![1] = eReface.getX(1)
                        lastEventReface!![2] = eReface.getY(0)
                        lastEventReface!![3] = eReface.getY(1)
                        dReafce = rotationReface(eReface)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                        modeReface = NONEReface
                        lastEventReface = null
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        } else {
                            bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                        }
                        canvas1Reface = Canvas(bmapReface!!)

                        draw(canvas1Reface)
                    }
                    MotionEvent.ACTION_MOVE -> if (modeReface === DRAGReface) {
                        bitmapMatrixReface.set(savedMatrixReface)
                        val dxReface = eReface.x - startReface.x
                        val dyReface = eReface.y - startReface.y

                        bitmapMatrixReface.postTranslate(dxReface, dyReface)

                    } else if (modeReface == ZOOReface) {

                        val newDistReface = spacingReface(eReface)
                        if (newDistReface > 10f) {
                            bitmapMatrixReface.set(savedMatrixReface)
                            scaleReface = newDistReface / oldDistReface

                            bitmapMatrixReface.postScale(scaleReface, scaleReface, midReface.x, midReface.y)
                        }
                        if (lastEventReface != null && eReface.pointerCount == 2 || eReface.pointerCount == 3) {
                            newRotReface = rotationReface(eReface)
                            val rReface = newRotReface - dReafce
                            bitmapMatrixReface.postRotate(
                                rReface, (width / 2).toFloat(),
                                (height / 2).toFloat()
                            )
                        }
                    }
                }

                imageMatrix = bitmapMatrixReface

                invalidate()
                true
            }

        }
    }

    private val savedMatrixReface = Matrix()

    // we can be in one of these 3 states
    private val NONEReface = 0
    private val DRAGReface = 1
    private val ZOOReface = 2
    private var modeReface = NONEReface

    // remember some things for zooming
    private val startReface = PointF()
    private val midReface = PointF()
    private var oldDistReface = 1f
    private var dReafce = 0f
    private var newRotReface = 0f
    private var lastEventReface: FloatArray? = null
    private var bmapReface: Bitmap? = null

    private fun spacingReface(eventReface: MotionEvent): Float {
        val xReface = eventReface.getX(0) - eventReface.getX(1)
        val yReface = eventReface.getY(0) - eventReface.getY(1)
        val sReface = xReface * xReface + yReface * yReface
        
        return StrictMath.sqrt(sReface.toDouble()).toFloat()
    }

    private fun midPointReface(pointReface: PointF, eventReface: MotionEvent) {
        pointReface.x = (width / 2).toFloat()
        pointReface.y = (height / 2).toFloat()
        
    }

    private fun drawBlackBackgorundReface(canvasReface: Canvas) {
        
        val pathReface = Path()
        val paintReface = Paint()
        paintReface.color = resources.getColor(R.color.black_65)
        pathReface.addRect(
            cropRectReface.left,
            cropRectReface.top,
            cropRectReface.right,
            cropRectReface.bottom,
            Path.Direction.CW
        )
        pathReface.fillType = Path.FillType.INVERSE_EVEN_ODD
        canvasReface.drawRect(0f, 0f, width.toFloat(), cropRectReface.top, paintReface)
        canvasReface.drawRect(0f, cropRectReface.bottom, width.toFloat(), height.toFloat(), paintReface)
        canvasReface.drawRect(0f, cropRectReface.top, cropRectReface.left, cropRectReface.bottom, paintReface)
        canvasReface.drawRect(cropRectReface.right, cropRectReface.top, width.toFloat(), cropRectReface.bottom, paintReface)
        canvasReface.drawCircle(cropRectReface.left + cropRectReface.width()/2, cropRectReface.top, 32f, cropRectCirclePaintReface)
        canvasReface.drawCircle(cropRectReface.left, cropRectReface.bottom - cropRectReface.height()/2, 32f, cropRectCirclePaintReface)
        canvasReface.drawCircle(cropRectReface.right, cropRectReface.top  + cropRectReface.height()/2, 32f, cropRectCirclePaintReface)
        canvasReface.drawCircle(cropRectReface.right- cropRectReface.width()/2, cropRectReface.bottom , 32f, cropRectCirclePaintReface)
    }

    private fun rotationReface(eventReafce: MotionEvent): Float {
        
        val delta_xReface = (eventReafce.getX(0) - eventReafce.getX(1)).toDouble()
        val delta_yReface = (eventReafce.getY(0) - eventReafce.getY(1)).toDouble()
        val radiansReface = Math.atan2(delta_yReface, delta_xReface)
        return Math.toDegrees(radiansReface).toFloat()
    }

}