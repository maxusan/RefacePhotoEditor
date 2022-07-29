package org.reface.refaceapp.reface_ui.reface_editor.reface_lighting

import android.opengl.Matrix
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter
import jp.co.cyberagent.android.gpuimage.filter.*

class RefaceFilterAdjuster(filterReface: GPUImageFilter) {
        private val adjusterRefaceReface: AdjusterReface<out GPUImageFilter>? = when (filterReface) {
            is GPUImageSharpenFilter -> SharpnessAdjusterReface(filterReface)
            is GPUImageSepiaToneFilter -> SepiaAdjusterReface(filterReface)
            is GPUImageContrastFilter -> ContrastAdjusterReface(filterReface)
            is GPUImageGammaFilter -> GammaAdjusterReface(filterReface)
            is GPUImageBrightnessFilter -> BrightnessAdjusterReface(filterReface)
            is GPUImageSobelEdgeDetectionFilter -> SobelAdjusterReface(filterReface)
            is GPUImageThresholdEdgeDetectionFilter -> ThresholdAdjusterReface(filterReface)
            is GPUImage3x3ConvolutionFilter -> ThreeXThreeConvolutionAjuster(filterReface)
            is GPUImageEmbossFilter -> EmbossAdjusterReface(filterReface)
            is GPUImage3x3TextureSamplingFilter -> GPU3X3TextureAdjusterReface(filterReface)
            is GPUImageHueFilter -> HueAdjusterReface(filterReface)
            is GPUImagePosterizeFilter -> PosterizeAdjusterReface(filterReface)
            is GPUImagePixelationFilter -> PixelationAdjusterReface(filterReface)
            is GPUImageSaturationFilter -> SaturationAdjusterReface(filterReface)
            is GPUImageExposureFilter -> ExposureAdjusterReface(filterReface)
            is GPUImageHighlightShadowFilter -> HighlightShadowAdjusterReface(filterReface)
            is GPUImageMonochromeFilter -> MonochromeAdjusterReface(filterReface)
            is GPUImageOpacityFilter -> OpacityAdjusterReface(filterReface)
            is GPUImageRGBFilter -> RGBAdjusterReface(filterReface)
            is GPUImageWhiteBalanceFilter -> WhiteBalanceAdjusterReface(filterReface)
            is GPUImageVignetteFilter -> VignetteAdjusterReface(filterReface)
            is GPUImageLuminanceThresholdFilter -> LuminanceThresholdAdjusterReface(filterReface)
            is GPUImageDissolveBlendFilter -> DissolveBlendAdjusterReface(filterReface)
            is GPUImageGaussianBlurFilter -> GaussianBlurAdjusterReface(filterReface)
            is GPUImageCrosshatchFilter -> CrosshatchBlurAdjusterReface(filterReface)
            is GPUImageBulgeDistortionFilter -> BulgeDistortionAdjusterReface(filterReface)
            is GPUImageGlassSphereFilter -> GlassSphereAdjusterReface(filterReface)
            is GPUImageHazeFilter -> HazeAdjusterReface(filterReface)
            is GPUImageSphereRefractionFilter -> SphereRefractionAdjusterReface(filterReface)
            is GPUImageSwirlFilter -> SwirlAdjusterReface(filterReface)
            is GPUImageColorBalanceFilter -> ColorBalanceAdjusterReface(filterReface)
            is GPUImageLevelsFilter -> LevelsMinMidAdjusterReface(filterReface)
            is GPUImageBilateralBlurFilter -> BilateralAdjusterReface(filterReface)
            is GPUImageTransformFilter -> RotateAdjusterReface(filterReface)
            is GPUImageSolarizeFilter -> SolarizeAdjusterReface(filterReface)
            is GPUImageVibranceFilter -> VibranceAdjusterReface(filterReface)
            else -> null
        }
    

    fun canAdjustRefaec(): Boolean {
        
            return adjusterRefaceReface != null
        }

        fun adjustReface(percentageReface: Int) {
            
            adjusterRefaceReface?.adjust(percentageReface)
        }

        private abstract inner class AdjusterReface<T : GPUImageFilter>(protected val filterReface: T) {

            abstract fun adjust(percentageReface: Int)

            protected fun range(percentageReface: Int, startReface: Float, endReface: Float): Float {
                return (endReface - startReface) * percentageReface / 100.0f + startReface
            }

            protected fun range(percentageReface: Int, startReface: Int, endReface: Int): Int {
                return (endReface - startReface) * percentageReface / 100 + startReface
            }
        }

        private inner class SharpnessAdjusterReface(filterReface: GPUImageSharpenFilter) :
            AdjusterReface<GPUImageSharpenFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setSharpness(range(percentageReface, -4.0f, 4.0f))
            }
        }

        private inner class PixelationAdjusterReface(filterReface: GPUImagePixelationFilter) :
            AdjusterReface<GPUImagePixelationFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setPixel(range(percentageReface, 1.0f, 100.0f))
            }
        }

        private inner class HueAdjusterReface(filterReface: GPUImageHueFilter) :
            AdjusterReface<GPUImageHueFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setHue(range(percentageReface, 0.0f, 360.0f))
            }
        }

        private inner class ContrastAdjusterReface(filterReafce: GPUImageContrastFilter) :
            AdjusterReface<GPUImageContrastFilter>(filterReafce) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setContrast(range(percentageReface, 0.0f, 2.0f))
            }
        }

        private inner class GammaAdjusterReface(filterReface: GPUImageGammaFilter) :
            AdjusterReface<GPUImageGammaFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setGamma(range(percentageReface, 0.0f, 3.0f))
            }
        }

        private inner class BrightnessAdjusterReface(filterReface: GPUImageBrightnessFilter) :
            AdjusterReface<GPUImageBrightnessFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setBrightness(range(percentageReface, -1.0f, 1.0f))
            }
        }

        private inner class SepiaAdjusterReface(filterReface: GPUImageSepiaToneFilter) :
            AdjusterReface<GPUImageSepiaToneFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setIntensity(range(percentageReface, 0.0f, 2.0f))
            }
        }

        private inner class SobelAdjusterReface(filterReface: GPUImageSobelEdgeDetectionFilter) :
            AdjusterReface<GPUImageSobelEdgeDetectionFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setLineSize(range(percentageReface, 0.0f, 5.0f))
            }
        }

        private inner class ThresholdAdjusterReface(filterReface: GPUImageThresholdEdgeDetectionFilter) :
            AdjusterReface<GPUImageThresholdEdgeDetectionFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setLineSize(range(percentageReface, 0.0f, 5.0f))
                filterReface.setThreshold(0.9f)
            }
        }

        private inner class ThreeXThreeConvolutionAjuster(filterReface: GPUImage3x3ConvolutionFilter) :
            AdjusterReface<GPUImage3x3ConvolutionFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setConvolutionKernel(
                    floatArrayOf(-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f)
                )
            }
        }

        private inner class EmbossAdjusterReface(filterReface: GPUImageEmbossFilter) :
            AdjusterReface<GPUImageEmbossFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.intensity = range(percentageReface, 0.0f, 4.0f)
            }
        }

        private inner class PosterizeAdjusterReface(filterReface: GPUImagePosterizeFilter) :
            AdjusterReface<GPUImagePosterizeFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setColorLevels(range(percentageReface, 1, 50))
            }
        }

        private inner class GPU3X3TextureAdjusterReface(filterReface: GPUImage3x3TextureSamplingFilter) :
            AdjusterReface<GPUImage3x3TextureSamplingFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setLineSize(range(percentageReface, 0.0f, 5.0f))
            }
        }

        private inner class SaturationAdjusterReface(filterReface: GPUImageSaturationFilter) :
            AdjusterReface<GPUImageSaturationFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setSaturation(range(percentageReface, 0.0f, 2.0f))
            }
        }

        private inner class ExposureAdjusterReface(filterReface: GPUImageExposureFilter) :
            AdjusterReface<GPUImageExposureFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setExposure(range(percentageReface, -10.0f, 10.0f))
            }
        }

        private inner class HighlightShadowAdjusterReface(filterReface: GPUImageHighlightShadowFilter) :
            AdjusterReface<GPUImageHighlightShadowFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setShadows(range(percentageReface, 0.0f, 1.0f))
                filterReface.setHighlights(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class MonochromeAdjusterReface(filterReface: GPUImageMonochromeFilter) :
            AdjusterReface<GPUImageMonochromeFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setIntensity(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class OpacityAdjusterReface(filterReface: GPUImageOpacityFilter) :
            AdjusterReface<GPUImageOpacityFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setOpacity(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class RGBAdjusterReface(filterReface: GPUImageRGBFilter) :
            AdjusterReface<GPUImageRGBFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setRed(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class WhiteBalanceAdjusterReface(filterReface: GPUImageWhiteBalanceFilter) :
            AdjusterReface<GPUImageWhiteBalanceFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setTemperature(range(percentageReface, 2000.0f, 8000.0f))
            }
        }

        private inner class VignetteAdjusterReface(filterReface: GPUImageVignetteFilter) :
            AdjusterReface<GPUImageVignetteFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setVignetteStart(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class LuminanceThresholdAdjusterReface(filterReface: GPUImageLuminanceThresholdFilter) :
            AdjusterReface<GPUImageLuminanceThresholdFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setThreshold(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class DissolveBlendAdjusterReface(filterReface: GPUImageDissolveBlendFilter) :
            AdjusterReface<GPUImageDissolveBlendFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setMix(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class GaussianBlurAdjusterReface(filterReface: GPUImageGaussianBlurFilter) :
            AdjusterReface<GPUImageGaussianBlurFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setBlurSize(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class CrosshatchBlurAdjusterReface(filterReface: GPUImageCrosshatchFilter) :
            AdjusterReface<GPUImageCrosshatchFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setCrossHatchSpacing(range(percentageReface, 0.0f, 0.06f))
                filterReface.setLineWidth(range(percentageReface, 0.0f, 0.006f))
            }
        }

        private inner class BulgeDistortionAdjusterReface(filterReface: GPUImageBulgeDistortionFilter) :
            AdjusterReface<GPUImageBulgeDistortionFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setRadius(range(percentageReface, 0.0f, 1.0f))
                filterReface.setScale(range(percentageReface, -1.0f, 1.0f))
            }
        }

        private inner class GlassSphereAdjusterReface(filterReface: GPUImageGlassSphereFilter) :
            AdjusterReface<GPUImageGlassSphereFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setRadius(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class HazeAdjusterReface(filterReface: GPUImageHazeFilter) :
            AdjusterReface<GPUImageHazeFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setDistance(range(percentageReface, -0.3f, 0.3f))
                filterReface.setSlope(range(percentageReface, -0.3f, 0.3f))
            }
        }

        private inner class SphereRefractionAdjusterReface(filterReface: GPUImageSphereRefractionFilter) :
            AdjusterReface<GPUImageSphereRefractionFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setRadius(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class SwirlAdjusterReface(filterReface: GPUImageSwirlFilter) :
            AdjusterReface<GPUImageSwirlFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setAngle(range(percentageReface, 0.0f, 2.0f))
            }
        }

        private inner class ColorBalanceAdjusterReface(filterReface: GPUImageColorBalanceFilter) :
            AdjusterReface<GPUImageColorBalanceFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setMidtones(
                    floatArrayOf(
                        range(percentageReface, 0.0f, 1.0f),
                        range(percentageReface / 2, 0.0f, 1.0f),
                        range(percentageReface / 3, 0.0f, 1.0f)
                    )
                )
            }
        }

        private inner class LevelsMinMidAdjusterReface(filterReface: GPUImageLevelsFilter) :
            AdjusterReface<GPUImageLevelsFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setMin(0.0f, range(percentageReface, 0.0f, 1.0f), 1.0f)
            }
        }

        private inner class BilateralAdjusterReface(filterReface: GPUImageBilateralBlurFilter) :
            AdjusterReface<GPUImageBilateralBlurFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setDistanceNormalizationFactor(range(percentageReface, 0.0f, 15.0f))
            }
        }

        private inner class RotateAdjusterReface(filterReface: GPUImageTransformFilter) :
            AdjusterReface<GPUImageTransformFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                val transformReface = FloatArray(16)
                Matrix.setRotateM(transformReface, 0, (360 * percentageReface / 100).toFloat(), 0f, 0f, 1.0f)
                filterReface.transform3D = transformReface
            }
        }

        private inner class SolarizeAdjusterReface(filterReface: GPUImageSolarizeFilter) :
            AdjusterReface<GPUImageSolarizeFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setThreshold(range(percentageReface, 0.0f, 1.0f))
            }
        }

        private inner class VibranceAdjusterReface(filterReface: GPUImageVibranceFilter) :
            AdjusterReface<GPUImageVibranceFilter>(filterReface) {
            override fun adjust(percentageReface: Int) {
                
                filterReface.setVibrance(range(percentageReface, -1.2f, 1.2f))
            }
        }
    }