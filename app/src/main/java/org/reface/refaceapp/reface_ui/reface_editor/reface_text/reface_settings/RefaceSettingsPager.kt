package org.reface.refaceapp.reface_ui.reface_editor.reface_text.reface_settings

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class RefaceSettingsPager(fragmentReface: Fragment): FragmentStateAdapter(fragmentReface) {
    private val fListReface = listOf(
        RefaceFontsFragment(),
        RefaceColorsListFragment()
    )
    
    override fun getItemCount(): Int {
        
        return fListReface.size
    }

    override fun createFragment(positionReface: Int): Fragment {
        
        return fListReface[positionReface]
    }
}