package org.reface.refaceapp.reface_ui.reface

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentMainBinding
import org.reface.refaceapp.reface_core.RefaceBitmapUtils
import org.reface.refaceapp.reface_core.RefaceListUtils.redoAvailableReface
import org.reface.refaceapp.reface_core.RefaceListUtils.undoAvailableReface
import org.reface.refaceapp.reface_core.RefaceMlUtils.runFaceContourDetectionReface
import org.reface.refaceapp.reface_core.RefaceSaveManager
import org.reface.refaceapp.reface_core.checkPermissions


class RefaceMainFragment : Fragment() {

    private lateinit var bindingReface: FragmentMainBinding
    private val viewModelReface: RefaceViewModel by activityViewModels()

    

    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentMainBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        viewModelReface.getBaseBitmapReface().observe(viewLifecycleOwner) {
            bindingReface.refaceImageview.setImageBmpReface(it)
            viewModelReface.addBitmapToListReface(it)
        }
        viewModelReface.getImageReface().observe(viewLifecycleOwner){
            viewModelReface.addBitmapToListReface(it)
        }
        viewModelReface.getBitmapIndexLiveReface().observe(viewLifecycleOwner) {
            if (it > -1 && it < viewModelReface.getBitmapsListReface().value!!.size) {
                val bitmapReface = viewModelReface.getBitmapsListReface().value!![it]
                bindingReface.refaceImageview.setImageBmpReface(bitmapReface)
            } else {
                bindingReface.refaceImageview.setImageBmpReface(null)
            }
            val redoAvailableReface = viewModelReface.getBitmapsListReface().value!!.redoAvailableReface(it)
            val undoAvailableReface = viewModelReface.getBitmapsListReface().value!!.undoAvailableReface(it)
            bindingReface.redoAvailable = redoAvailableReface
            bindingReface.undoAvailable = undoAvailableReface
            bindingReface.executePendingBindings()
        }
        bindingReface.chooseImageButton.setOnClickListener {
            startImagePickerReface()
        }
        bindingReface.flipButton.setOnClickListener {
            bindingReface.refaceImageview.getImageBmpReface()?.let {
                val flippedBitmapReface = RefaceBitmapUtils.flipBitmapVerticallyReface(it)
                viewModelReface.addBitmapToListReface(flippedBitmapReface)
            }
        }
        bindingReface.refaceCancelButton.setOnClickListener {
            viewModelReface.clearBitmapListReface()
        }
        bindingReface.undoButton.setOnClickListener {
            viewModelReface.undoReface()
        }
        bindingReface.redoButton.setOnClickListener {
            viewModelReface.redoReface()
        }
        bindingReface.refaceEditButton.setOnClickListener {
            kotlin.runCatching {
                viewModelReface.setImageReface(viewModelReface.getBitmapsListReface().value!![viewModelReface.getBitmapIndexReface()])
                findNavController().navigate(
                    RefaceMainFragmentDirections.actionMainFragmentToEditorFragment(
                    )
                )
            }
        }
        bindingReface.maskButton.setOnClickListener {
            kotlin.runCatching {
                bindingReface.refaceImageview.getImageBmpReface()?.let {
                    runFaceContourDetectionReface(it) {
                        bindingReface.refaceImageview.facesReface = it
                    }
                }
                findNavController().navigate(
                    RefaceMainFragmentDirections.actionMainFragmentToShapesBottomSheetFragment()
                )
            }
        }
        bindingReface.swapButton.setOnClickListener {
            bindingReface.swapProgressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                bindingReface.refaceImageview.flipFacesReface() {
                    lifecycleScope.launch(Dispatchers.Main) {
                        if (it != null) {
                            viewModelReface.addBitmapToListReface(it)
                        }
                        bindingReface.swapProgressBar.visibility = View.GONE
                    }
                }
            }
        }
        bindingReface.copyButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO){
                bindingReface.refaceImageview.saveFacesReface()
            }
        }
        bindingReface.pasteButton.setOnClickListener {
            bindingReface.refaceImageview.pasteFacesReface(){
                if(it != null){
                    viewModelReface.addBitmapToListReface(it)
                }
            }
        }
        
        viewModelReface.refaceShapeClickReface.observe(viewLifecycleOwner) {
            bindingReface.refaceImageview.refaceShape = it
        }
        bindingReface.refaceSaveButton.setOnClickListener {
            checkPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) {
                if (it) {
                    bindingReface.refaceImageview.getImageBmpReface()?.let {
                        RefaceSaveManager.saveImageReface(requireContext(), it)
                    }
                }
            }
        }
    }

    private fun startImagePickerReface() {
        
        ImagePicker
            .with(this)
            .crop()
            .createIntent { intentReface ->
                pickerReface.launch(intentReface)
            }
    }

    private val pickerReface =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultReface: ActivityResult ->
            val resultCodeReface = resultReface.resultCode
            val dataReface = resultReface.data
            when (resultCodeReface) {
                Activity.RESULT_OK -> {
                    val fileUriReface = dataReface?.data!!
                    Glide.with(this)
                        .asBitmap()
                        .load(fileUriReface)
                        .into(object : CustomTarget<Bitmap?>() {
                            override fun onResourceReady(
                                resourceReface: Bitmap,
                                transitionReface: Transition<in Bitmap?>?
                            ) {
                                
                                viewModelReface.setBaseBitmapReface(resourceReface)
                            }

                            override fun onLoadCleared(placeholderReface: Drawable?) {
                                
                            }

                        })
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(dataReface), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }
}