package com.app.i_express_rider.view.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.service.autofill.Validators
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.databinding.ActivityLoginBinding
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var dataViewModel: DataViewModel
    private var _binding: ActivityLoginBinding? = null
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

        _binding = ActivityLoginBinding.inflate(layoutInflater)
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


        binding.createAccountLink.setOnClickListener {
             Constant.gotoPhoneActivity = 1
            startActivity(
                Intent(
                    this@LoginActivity,
                    PhoneActivity::class.java
                )
            )
        }
            binding.rectangleContinue.setOnClickListener {

                if(!binding.phoneEt.text.toString().trim().equals("")
                    && valid_mobile(binding.phoneEt.text.toString())){

                    var intent = Intent(this,PasswordActivity::class.java)
                    intent.putExtra("number",binding.phoneEt.text.toString().trim())
                    startActivity(intent)

                }else{

                    Toast.makeText(this,"Please Enter Your Phone Number",Toast.LENGTH_LONG).show()
                }
            }
    }

    fun valid_mobile ( value:String ):Boolean {

        if(value.matches(Regex("^(?:\\+88|88)?(01[3-9]\\d{8})$"))){
            return true
        }else
            return false
    }


}


