package org.reface.refaceapp.reface_ui.reface_editor.reface_crop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import org.reface.refaceapp.R
import java.lang.Math.abs


class RefaceCropImageView(contextReface: Context, attributeSetReface: AttributeSet) :
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

    var isCustomReface = false

    enum class ResolutionReface {
        ORIGINAL, SQUARE, S4TO3, S6TO4, S7TO5, S10TO8, S16TO9
    }

    private var resolutionReface: ResolutionReface? = null


    fun setOriginalReface() {
        resolutionReface = ResolutionReface.ORIGINAL
        
        setResolutionsReface()
        invalidate()
    }

    fun setSquareReface() {
        resolutionReface = ResolutionReface.SQUARE
        setResolutionsReface()
        
        invalidate()
    }

    fun setSquare4to3() {
        resolutionReface = ResolutionReface.S4TO3
        setResolutionsReface()
        
        invalidate()
    }

    fun setSquare6to4() {
        resolutionReface = ResolutionReface.S6TO4
        setResolutionsReface()
        
        invalidate()
    }

    fun setSquare7to5() {
        resolutionReface = ResolutionReface.S7TO5
        setResolutionsReface()
        
        invalidate()
    }

    fun setSquare10to8() {
        resolutionReface = ResolutionReface.S10TO8
        setResolutionsReface()
        
        invalidate()
    }

    fun setSquare16to9() {
        resolutionReface = ResolutionReface.S16TO9
        setResolutionsReface()
        
        invalidate()
    }

    var flagSaveReface = false
    var scaleReface = 0f


    fun getImageBmpReface(): Bitmap? {
        flagSaveReface = true
        invalidate()
        
        if (canvas1Reface != null)
            draw(canvas1Reface)
        return try {
            Bitmap.createBitmap(
                bmapReface!!,
                cropRectReface.left.toInt(),
                cropRectReface.top.toInt(),
                cropRectReface.width().toInt(),
                cropRectReface.height().toInt()
            )
        } catch (eReface: Exception) {
            eReface.printStackTrace()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            } else {
                bmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            }
            canvas1Reface = Canvas(bmapReface!!)

            draw(canvas1Reface)
            getImageBmpReface()
        }
    }


    private var canvas1Reface: Canvas? = null

    private var cropRectStartReface = RectF()


    enum class RectOrImage {
        LEFT, TOP, RIGHT, BOTTOM, NONE
    }


    private var recReface: RectOrImage? = null

    private var leftOriginalReface: Float? = null
    private var rightOriginalReface: Float? = null
    private var topOriginalReface: Float? = null
    private var bottomOriginalReface: Float? = null
    var heightRealReface = MutableLiveData<Int>()

    private fun setResolutionsReface() {
        val valuesReface = FloatArray(9)
        bitmapMatrixReface.getValues(valuesReface)
        
        heightRealReface.postValue((height.toFloat()).toInt())
        when (resolutionReface) {
            ResolutionReface.ORIGINAL -> {
                if (leftOriginalReface == null || bottomOriginalReface!! < 0f) {
                    leftOriginalReface = valuesReface[Matrix.MTRANS_X] + bitmapReface!!.width / 34
                    topOriginalReface = valuesReface[Matrix.MTRANS_Y] + bitmapReface!!.height / 34
                    rightOriginalReface = width.toFloat() - valuesReface[Matrix.MTRANS_X] - bitmapReface!!.width / 34
                    bottomOriginalReface =
                        height.toFloat() - valuesReface[Matrix.MTRANS_Y] - bitmapReface!!.height / 34
                }
                cropRectReface.left = leftOriginalReface!!
                cropRectReface.top = topOriginalReface!!
                cropRectReface.right = rightOriginalReface!!
                cropRectReface.bottom = bottomOriginalReface!!
            }
            ResolutionReface.SQUARE -> {
                cropRectReface.left = width / 2 - (width * 0.4).toFloat()
                cropRectReface.top = height / 2 - (width * 0.4).toFloat()
                cropRectReface.right = width / 2 + (width * 0.4).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.4).toFloat()
            }
            ResolutionReface.S4TO3 -> {
                cropRectReface.left = width / 2 - (width * 0.3 * 4 / 3).toFloat()
                cropRectReface.top = height / 2 - (width * 0.3).toFloat()
                cropRectReface.right = width / 2 + (width * 0.3 * 4 / 3).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.3).toFloat()
            }

            ResolutionReface.S6TO4 -> {
                cropRectReface.left = width / 2 - (width * 0.3 * 6 / 4).toFloat()
                cropRectReface.top = height / 2 - (width * 0.3).toFloat()
                cropRectReface.right = width / 2 + (width * 0.3 * 6 / 4).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.3).toFloat()
            }
            ResolutionReface.S7TO5 -> {
                cropRectReface.left = width / 2 - (width * 0.3 * 7 / 5).toFloat()
                cropRectReface.top = height / 2 - (width * 0.3).toFloat()
                cropRectReface.right = width / 2 + (width * 0.3 * 7 / 5).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.3).toFloat()
            }
            ResolutionReface.S10TO8 -> {
                cropRectReface.left = width / 2 - (width * 0.3 * 10 / 8).toFloat()
                cropRectReface.top = height / 2 - (width * 0.3).toFloat()
                cropRectReface.right = width / 2 + (width * 0.3 * 10 / 8).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.3).toFloat()
            }
            ResolutionReface.S16TO9 -> {
                cropRectReface.left = width / 2 - (width * 0.25 * 16 / 9).toFloat()
                cropRectReface.top = height / 2 - (width * 0.25).toFloat()
                cropRectReface.right = width / 2 + (width * 0.25 * 16 / 9).toFloat()
                cropRectReface.bottom = height / 2 + (width * 0.25).toFloat()
            }
        }
    }

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
                    setResolutionsReface()
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
                canvasReface.drawCircle(
                    cropRectReface.left + cropRectReface.width() / 2,
                    cropRectReface.top,
                    32f,
                    cropRectCirclePaintReface
                )
                canvasReface.drawCircle(
                    cropRectReface.left,
                    cropRectReface.bottom - cropRectReface.height() / 2,
                    32f,
                    cropRectCirclePaintReface
                )
                canvasReface.drawCircle(
                    cropRectReface.right,
                    cropRectReface.top + cropRectReface.height() / 2,
                    32f,
                    cropRectCirclePaintReface
                )
                canvasReface.drawCircle(
                    cropRectReface.right - cropRectReface.width() / 2,
                    cropRectReface.bottom,
                    32f,
                    cropRectCirclePaintReface
                )
                drawBlackBackgorund(canvasReface)

            }

            setOnTouchListener { vReface, eventReface ->
                when (eventReface.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        savedMatrixReface.set(bitmapMatrixReface)
                        startReface[eventReface.x] = eventReface.y

                        cropRectStartReface.set(cropRectReface)
                        val leftCropStartReface = abs(startReface.x - cropRectStartReface.left) < 50
                        val rightCropStartReface = abs(startReface.x - cropRectStartReface.right) < 50
                        val topCropStartReface = abs(startReface.y - cropRectStartReface.top) < 50
                        val bottomCropStartReface = abs(startReface.y - cropRectStartReface.bottom) < 50

                        recReface = when {
                            leftCropStartReface -> {
                                RectOrImage.LEFT
                            }
                            rightCropStartReface -> {
                                RectOrImage.RIGHT
                            }
                            topCropStartReface -> {
                                RectOrImage.TOP
                            }
                            bottomCropStartReface -> {
                                RectOrImage.BOTTOM
                            }
                            else -> RectOrImage.NONE
                        }
                        modeReface = DRAGReface
                        lastEventReface = null
                    }
                    MotionEvent.ACTION_POINTER_DOWN -> {
                        oldDistReface = spacingReface(eventReface)
                        if (oldDistReface > 10f) {
                            savedMatrixReface.set(bitmapMatrixReface)
                            midPointReface(midReface, eventReface)
                            modeReface = ZOOMReface
                        }
                        lastEventReface = FloatArray(4)
                        lastEventReface!![0] = eventReface.getX(0)
                        lastEventReface!![1] = eventReface.getX(1)
                        lastEventReface!![2] = eventReface.getY(0)
                        lastEventReface!![3] = eventReface.getY(1)
                        dReface = rotationReface(eventReface)
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
                    MotionEvent.ACTION_MOVE -> if (modeReface == DRAGReface) {
                        bitmapMatrixReface.set(savedMatrixReface)
                        val dxReface = eventReface.x - startReface.x
                        val dyReface = eventReface.y - startReface.y
                        val valuesReface = FloatArray(9)
                        bitmapMatrixReface.getValues(valuesReface)


                        val f1AbsReface = valuesReface[Matrix.MTRANS_X]
                        val f2AbsReface = valuesReface[Matrix.MTRANS_Y]
                        val topLeftAbsReface = PointF()
                        topLeftAbsReface.set(f1AbsReface, f2AbsReface)
                        if (isCustomReface)
                            when (recReface) {
                                RectOrImage.LEFT -> {
                                    if ((cropRectReface.right - eventReface.x >= 50 || dxReface < 10) && eventReface.x > 10)
                                        cropRectReface.left = eventReface.x
                                }
                                RectOrImage.RIGHT -> {
                                    if ((cropRectReface.left - eventReface.x <= -50 || dxReface > 10) && eventReface.x < width - 10)
                                        cropRectReface.right = eventReface.x
                                }
                                RectOrImage.TOP -> {
                                    if ((cropRectReface.bottom - eventReface.y >= 50 || dyReface < 10) && eventReface.y > 10)
                                        cropRectReface.top = eventReface.y
                                }
                                RectOrImage.BOTTOM -> {
                                    if ((cropRectReface.top - eventReface.y <= -50 || dyReface > 10) && eventReface.y < height - 10)
                                        cropRectReface.bottom = eventReface.y
                                }
                                else -> bitmapMatrixReface.postTranslate(dxReface, dyReface)
                            }
                        else
                            bitmapMatrixReface.postTranslate(dxReface, dyReface)

                    } else if (modeReface == ZOOMReface) {

                        val newDistReface = spacingReface(eventReface)
                        if (newDistReface > 10f) {
                            bitmapMatrixReface.set(savedMatrixReface)
                            scaleReface = newDistReface / oldDistReface

                            bitmapMatrixReface.postScale(scaleReface, scaleReface, midReface.x, midReface.y)
                        }
                        if (lastEventReface != null && eventReface.pointerCount == 2 || eventReface.pointerCount == 3) {
                            newRotReface = rotationReface(eventReface)
                            val rReface = newRotReface - dReface
                            val valuesReface = FloatArray(9)
                            bitmapMatrixReface.getValues(valuesReface)
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

    private val NONEReface = 0
    private val DRAGReface = 1
    private val ZOOMReface = 2
    private var modeReface = NONEReface

    private val startReface = PointF()
    private val midReface = PointF()
    private var oldDistReface = 1f
    private var dReface = 0f
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

    private fun drawBlackBackgorund(canvasReface: Canvas) {
        
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
        canvasReface.drawCircle(
            cropRectReface.left + cropRectReface.width() / 2,
            cropRectReface.top,
            32f,
            cropRectCirclePaintReface
        )
        canvasReface.drawCircle(
            cropRectReface.left,
            cropRectReface.bottom - cropRectReface.height() / 2,
            32f,
            cropRectCirclePaintReface
        )
        canvasReface.drawCircle(
            cropRectReface.right,
            cropRectReface.top + cropRectReface.height() / 2,
            32f,
            cropRectCirclePaintReface
        )
        canvasReface.drawCircle(
            cropRectReface.right - cropRectReface.width() / 2,
            cropRectReface.bottom,
            32f,
            cropRectCirclePaintReface
        )
    }

    private fun rotationReface(eventReface: MotionEvent): Float {
        
        val delta_xReface = (eventReface.getX(0) - eventReface.getX(1)).toDouble()
        val delta_yReface = (eventReface.getY(0) - eventReface.getY(1)).toDouble()
        val radiansReface = Math.atan2(delta_yReface, delta_xReface)
        return Math.toDegrees(radiansReface).toFloat()
    }

}