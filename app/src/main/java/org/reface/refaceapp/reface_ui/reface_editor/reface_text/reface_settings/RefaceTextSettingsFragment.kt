package org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import org.reface.refaceapp.R
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.FragmentTextSettingsBinding


class RefaceTextSettingsFragment : BottomSheetDialogFragment() {

    private lateinit var bindingReface: FragmentTextSettingsBinding
    private lateinit var adapRefave: RefaceSettingsPager
    private val viewModelReface: RefaceViewModel by activityViewModels()
    
    override fun getTheme(): Int = R.style.CustomBottomSheetDialogTheme

    override fun onCreateView(
        inflaterReface: LayoutInflater, containerReface: ViewGroup?,
        savedInstanceStateReface: Bundle?
    ): View {
        bindingReface = FragmentTextSettingsBinding.inflate(inflaterReface)
        
        return bindingReface.root
    }

    override fun onViewCreated(viewReface: View, savedInstanceStateReface: Bundle?) {
        super.onViewCreated(viewReface, savedInstanceStateReface)
        disableDismissByMotion()
        dialog?.window?.setDimAmount(0f)
        adapRefave = RefaceSettingsPager(this)
        bindingReface.pages.adapter = adapRefave
        bindingReface.pages.isUserInputEnabled = false
        bindingReface.color.setOnClickListener {
            viewModelReface.setSettingsModeReface(RefaceTextSettingsMode.COLOR)
        }
        bindingReface.font.setOnClickListener {
            viewModelReface.setSettingsModeReface(RefaceTextSettingsMode.FONT)
        }
        viewModelReface.getSettingsModeReface().observe(viewLifecycleOwner) {
            bindingReface.refaceMode = it
            when (it) {
                RefaceTextSettingsMode.COLOR -> bindingReface.pages.currentItem = 1
                RefaceTextSettingsMode.FONT -> bindingReface.pages.currentItem = 0
            }
        }
        bindingReface.openKeyboard.setOnClickListener {
            bindingReface.et.requestFocus()
            val inputMethodManagerReface = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManagerReface.toggleSoftInputFromWindow(bindingReface.et.applicationWindowToken, InputMethodManager.SHOW_FORCED,
                0)
        }
        bindingReface.et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0Reface: CharSequence?, p1Reface: Int, p2Reface: Int, p3Reface: Int) {
                
            }

            override fun onTextChanged(p0Reface: CharSequence?, p1Reface: Int, p2Reface: Int, p3Reface: Int) {
                
                viewModelReface.setCurrentTextReface(p0Reface.toString())
            }

            override fun afterTextChanged(p0Reface: Editable?) {
                
            }
        })

        setEventListener(
            requireActivity(),
            KeyboardVisibilityEventListener {
                bindingReface.keyboard = it
            })
        bindingReface.refaceDone.setOnClickListener {
            dismiss()
        }
    }
    private fun disableDismissByMotion() {
        if (dialog is BottomSheetDialog) {
            isCancelable = false
            val behaviourReface = (dialog as BottomSheetDialog).behavior
            behaviourReface.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheetReface: View, newStateReface: Int) {
                    if (newStateReface == BottomSheetBehavior.STATE_DRAGGING) {
                        
                        behaviourReface.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheetReface: View, slideOffsetReface: Float) {
                    
                }
            })
        }
    }
}