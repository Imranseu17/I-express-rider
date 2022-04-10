package com.app.i_express_rider.view.ui

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.Model.Presenter.VerifyTokenPresenter
import com.app.i_express_rider.Model.callback.VerifyTokenUserView
import com.app.i_express_rider.Model.models.GetVerifyToken
import com.app.i_express_rider.databinding.ActivityOtpactivityBinding
import com.app.i_express_rider.view.utils.Constant
import com.app.i_express_rider.view.utils.OTP_Receiver
import com.app.i_express_rider.viewmodel.DataViewModel


class OTPActivity : AppCompatActivity(),VerifyTokenUserView {
    private lateinit var dataViewModel: DataViewModel
    private var _binding: ActivityOtpactivityBinding? = null
    private var currentApiVersion = 0
    private val binding get() = _binding!!
    private var verifyTokenPresenter:VerifyTokenPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dataViewModel =
            ViewModelProvider(this). get(DataViewModel::class.java)

        _binding = ActivityOtpactivityBinding.inflate(layoutInflater)
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

        binding.progressBar.visibility = View.VISIBLE
        binding.bodyLayout.visibility = View.GONE



        verifyTokenPresenter = VerifyTokenPresenter(this)

        //we need to ask user permission to auto read sms
        //we need to ask user permission to auto read sms
        if(OTP_Receiver().abortBroadcast){
            binding.progressBar.visibility = View.VISIBLE
            binding.bodyLayout.visibility = View.GONE
        }else{
            binding.progressBar.visibility = View.GONE
            binding.bodyLayout.visibility = View.VISIBLE
        }

        requestsmspermission()
        OTP_Receiver().setEditText(_binding?.otpView)

        binding.back.setOnClickListener {
            finish()
        }

        binding.verify.setOnClickListener{
            try {

                var country_code:String? = intent.getStringExtra("country_code")
                var phone_number:String? = intent.getStringExtra("phone_number")
                var  otp:Int = _binding?.otpView?.otp?.toInt()!!

                if (!_binding?.otpView?.otp?.equals("")!!){

                    verifyOTP(country_code,phone_number,otp)
                }else{
                    Toast.makeText(
                        this, "Please give OTP number",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }catch (e:Exception){
                e.printStackTrace()
            }

        }

    }

    fun verifyOTP(code: String?, phone_number:String?,otp:Int) {
        if (checkConnection()) {
            verifyTokenPresenter?.attemptVerifyToken(code,phone_number,otp)
        } else Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()

    }

    override fun onSuccess(getVerifyToken: GetVerifyToken?, code: Int) {
        Toast.makeText(this,getVerifyToken?.message, Toast.LENGTH_SHORT).show()
        if(Constant.gotoPhoneActivity ==1){

            var country_code:String? = intent.getStringExtra("country_code")
            var phone_number:String? = intent.getStringExtra("phone_number")
            var verifyToken = getVerifyToken?.data?.verifyToken

            val intent = Intent(
                this@OTPActivity,
                RegistrationActivity::class.java
            )

            intent.putExtra("country_code",country_code)
            intent.putExtra("phone_number",phone_number)
            intent.putExtra("verify_token",verifyToken)

            startActivity(intent)

        }else{
            startActivity(
                Intent(
                    this@OTPActivity,
                    ResetPasswordActivity::class.java
                )
            )
        }
    }

    override fun onError(error: String?, code: Int) {
        Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
    }

    private fun checkConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    private fun requestsmspermission() {
        val smspermission: String = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, smspermission)
        //check if read SMS permission is granted or not
        if (grant != PackageManager.PERMISSION_GRANTED) {
            val permission_list = arrayOfNulls<String>(1)
            permission_list[0] = smspermission
            ActivityCompat.requestPermissions(this, permission_list, 1)
        }
    }
}