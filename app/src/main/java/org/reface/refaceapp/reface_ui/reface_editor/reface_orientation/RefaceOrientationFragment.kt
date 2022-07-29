package org.reface.refaceapp.reface_ui.reface_editor.reface_orientation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentOrientationBinding

class RefaceOrientationFragment : Fragment() {
    private lateinit var bindingReface: FragmentOrientationBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()

    

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentOrientationBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getImageReface().observe(viewLifecycleOwner) {
            bindingReface.refaceImageview.setImageBmpReface(it)
        }
        
        bindingReface.btnLeft.setOnClickListener {
            bindingReface.refaceImageview.setImageBmpReface(bindingReface.refaceImageview.rotateBitmapReface(-90f))
        }

        bindingReface.btnRight.setOnClickListener {
            bindingReface.refaceImageview.setImageBmpReface(bindingReface.refaceImageview.rotateBitmapReface(90f))
        }

        bindingReface.btnVertical.setOnClickListener {
            bindingReface.refaceImageview.setImageBmpReface(bindingReface.refaceImageview.flipBitmapReface(true, false))
        }

        bindingReface.btnHorizontal.setOnClickListener {
            bindingReface.refaceImageview.setImageBmpReface(bindingReface.refaceImageview.flipBitmapReface(false, true))
        }

        bindingReface.refaceDone.setOnClickListener {
            viewModelReface.setImageReface(bindingReface.refaceImageview.getImageBmpReface())
            findNavController().popBackStack()
        }
    }
}