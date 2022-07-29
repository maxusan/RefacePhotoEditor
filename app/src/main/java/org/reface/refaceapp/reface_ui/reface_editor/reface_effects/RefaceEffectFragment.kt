package org.reface.refaceapp.reface_ui.reface_editor.reface_effects

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentEffectBinding
import org.reface.refaceapp.reface_ui.reface_editor.getFilter

class RefaceEffectFragment : Fragment() {
    private lateinit var bindingReface: FragmentEffectBinding
    private val adapterReface by lazy {
        EffectAdapter {
            bindingReface.refaceImageview.setImageBmpReface(it.bitmapReface)
            newBitmapReface = it.bitmapReface
        }
    }
    private val viewModelReface: RefaceViewModel by activityViewModels()
    
    private var newBitmapReface: Bitmap? = null

    override fun onCreateView(
        inflaterReface: LayoutInflater,
        containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentEffectBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        bindingReface.recyclerFilters.adapter = adapterReface

        viewModelReface.getImageReface().observe(viewLifecycleOwner) {
            val bitmapReface = Bitmap.createBitmap(it!!.copy(Bitmap.Config.ARGB_8888, true))
            bindingReface.refaceImageview.setImageBmpReface(bitmapReface)
            adapterReface.submitList(getFilter(requireContext(), bitmapReface))
            newBitmapReface = bitmapReface
        }
        
        bindingReface.refaceCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        bindingReface.refaceDone.setOnClickListener {
            viewModelReface.setImageReface(newBitmapReface!!)
            findNavController().popBackStack()
        }
    }
}