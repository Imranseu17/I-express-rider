package com.app.i_express_rider.view.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.Model.Presenter.LoginPresenter
import com.app.i_express_rider.Model.callback.LoginUserView
import com.app.i_express_rider.Model.models.Login
import com.app.i_express_rider.databinding.ActivityPasswordBinding
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel

class PasswordActivity : AppCompatActivity(),LoginUserView {

    private lateinit var dataViewModel: DataViewModel
    private var _binding: ActivityPasswordBinding? = null
    private var currentApiVersion = 0
    private val binding get() = _binding!!
    private var loginPresenter:LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dataViewModel =
            ViewModelProvider(this). get(DataViewModel::class.java)

        _binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginPresenter = LoginPresenter(this)
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
        binding.backButton .setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.rectangleLogin.setOnClickListener(View.OnClickListener {

            var phoneNumber = intent.getStringExtra("number")
            var password = binding.passEt.text.toString().trim()

            if(!password.equals("")){
                loginPresenter?.attemptLogin(phoneNumber,password)
            }else{

                Toast.makeText(this@PasswordActivity,"Please Enter Your Password",Toast.LENGTH_LONG).show()
            }

        })

        binding.forgetPassLink.setOnClickListener{
            Constant.gotoPhoneActivity = 2
            startActivity(
                Intent(
                    this@PasswordActivity,
                    PhoneActivity::class.java
                )
            )
        }
    }

    override fun onSuccess(login: Login?, code: Int) {
        startActivity(
            Intent(
                this@PasswordActivity,
                MainActivity::class.java
            )
        )
    }

    override fun onError(error: String?, code: Int) {
        Toast.makeText(this@PasswordActivity,error,Toast.LENGTH_LONG).show()
    }
}