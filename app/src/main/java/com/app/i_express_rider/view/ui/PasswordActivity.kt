package com.app.i_express_rider.view.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.Model.Presenter.LoginPresenter
import com.app.i_express_rider.Model.Presenter.SendOtpPresenter
import com.app.i_express_rider.Model.callback.LoginUserView
import com.app.i_express_rider.Model.callback.OTPUserView
import com.app.i_express_rider.Model.models.Login
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityPasswordBinding
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.viewmodel.DataViewModel

class PasswordActivity : AppCompatActivity(),LoginUserView
    {

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
        loginPresenter = LoginPresenter(this)
        binding.backButton .setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.rectangleLogin.setOnClickListener(View.OnClickListener {

            var type:String? = "phone"
            var country_code:String? = intent.getStringExtra("country_code")
            var phone_number:String? = intent.getStringExtra("phone_number")
            var password = binding.passEt.text.toString().trim()
            var rememberMe = false
            var appId = "APP-I2345"
            var ipAddress = "192.168.0.118"

            if(!password.equals("")){

                login(type,country_code,phone_number,password,rememberMe,appId,ipAddress)

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

        fun login(type:String? , country_code:String? ,phone:String?,
                         password:String, rememberMe:Boolean,appId:String,
                         ipAddress: String ){

            if (checkConnection()) {
                loginPresenter?.attemptLogin(type,country_code,phone,password,rememberMe,appId,ipAddress)
            } else Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()

        }

        override fun onSuccess(login: Login?, code: Int) {
            Toast.makeText(this,login?.message,Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(
                    this@PasswordActivity,
                    MainActivity::class.java
                )
            )
        }

        override fun onError(error: String?, code: Int) {
            Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
        }

        private fun checkConnection(): Boolean {
            val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }


    }