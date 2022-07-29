package org.reface.refaceapp.reface_ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import org.reface.refaceapp.RefaceViewModel
import org.reface.refaceapp.databinding.ActivityMainBinding

class RefaceMainActivity : AppCompatActivity() {

    private lateinit var bindingReface: ActivityMainBinding
    private val viewModelReface: RefaceViewModel by viewModels()
    
    override fun onCreate(savedInstanceStateReface: Bundle?) {
        super.onCreate(savedInstanceStateReface)
        bindingReface = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingReface.root)
        
    }
}