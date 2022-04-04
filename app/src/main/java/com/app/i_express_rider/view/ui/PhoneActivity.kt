package com.app.i_express_rider.view.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityLoginBinding
import com.app.i_express_rider.databinding.ActivityPhoneBinding
import com.app.i_express_rider.viewmodel.DataViewModel

class PhoneActivity : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel
    private var _binding: ActivityPhoneBinding? = null
    private var currentApiVersion = 0
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dataViewModel =
            ViewModelProvider(this). get(DataViewModel::class.java)

        _binding = ActivityPhoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentApiVersion = Build.VERSION.SDK_INT
        val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
            window.decorView.systemUiVisibility = flags
            val decorView = window.decorView
            decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                    decorView.systemUiVisibility = flags
                }
            }
        }

        binding.back.setOnClickListener {
            finish()
        }

        binding.next.setOnClickListener{
            startActivity(
                Intent(
                    this@PhoneActivity,
                    OTPActivity::class.java
                )
            )
        }

    }
}