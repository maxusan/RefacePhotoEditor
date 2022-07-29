package org.reface.refaceapp.reface_ui.reface_editor.reface_crop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentCropBinding

class RefaceCropFragment : Fragment() {
    private lateinit var bindingReface: FragmentCropBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()

    

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentCropBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner) {
            bindingReface.refaceImageview.setImageBmpReface(it)

            

            bindingReface.refaceList = listOf(true, false, false, false, false, false, false, false)
            bindingReface.refaceImageview.isCustomReface = true
            bindingReface.refaceImageview.setOriginalReface()

            bindingReface.llCrop.forEachIndexed { indexReface, viewReface ->

                viewReface.setOnClickListener {
                    when (indexReface) {
                        0 -> {
                            bindingReface.refaceImageview.isCustomReface = true
                            bindingReface.refaceList =
                                listOf(true, false, false, false, false, false, false, false)

                        }
                        1 -> {
                            bindingReface.refaceImageview.setOriginalReface()
                            bindingReface.refaceList =
                                listOf(false, true, false, false, false, false, false, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        2 -> {
                            bindingReface.refaceImageview.setSquareReface()
                            bindingReface.refaceList =
                                listOf(false, false, true, false, false, false, false, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        3 -> {
                            bindingReface.refaceImageview.setSquare4to3()
                            bindingReface.refaceList =
                                listOf(false, false, false, true, false, false, false, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        4 -> {
                            bindingReface.refaceImageview.setSquare6to4()
                            bindingReface.refaceList =
                                listOf(false, false, false, false, true, false, false, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        5 -> {
                            bindingReface.refaceImageview.setSquare7to5()
                            bindingReface.refaceList =
                                listOf(false, false, false, false, false, true, false, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        6 -> {
                            bindingReface.refaceImageview.setSquare10to8()
                            bindingReface.refaceList =
                                listOf(false, false, false, false, false, false, true, false)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                        7 -> {
                            bindingReface.refaceImageview.setSquare16to9()
                            bindingReface.refaceList =
                                listOf(false, false, false, false, false, false, false, true)
                            bindingReface.refaceImageview.isCustomReface = false
                        }
                    }
                }
            }
        }


        bindingReface.refaceDone.setOnClickListener {
            viewModelReface.setImageReface(bindingReface.refaceImageview.getImageBmpReface())
            findNavController().popBackStack()
        }
    }
}