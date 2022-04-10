package com.app.i_express_rider.view.ui

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.i_express_rider.Model.Presenter.SendOtpPresenter
import com.app.i_express_rider.Model.callback.OTPUserView
import com.app.i_express_rider.Model.models.SendOTP
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityPhoneBinding
import com.app.i_express_rider.viewmodel.DataViewModel
import com.rejowan.cutetoast.CuteToast

class PhoneActivity : AppCompatActivity(),OTPUserView {
    private lateinit var dataViewModel: DataViewModel
    private var _binding: ActivityPhoneBinding? = null
    private var currentApiVersion = 0
    private val binding get() = _binding!!
    private var sendOtpPresenter:SendOtpPresenter ? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        dataViewModel =
            ViewModelProvider(this).get(DataViewModel::class.java)

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

        binding.codeEt.setText("+880")

        sendOtpPresenter = SendOtpPresenter(this)

        binding.back.setOnClickListener {
            finish()
        }

        binding.next.setOnClickListener {
            val code: String = _binding?.codeEt?.text.toString()
            val phone_number: String = _binding?.phoneEt?.text.toString()
            try {
                if (!code.equals("") && !phone_number.equals("")) {
                    sendOTP(code, phone_number)
                } else Toast.makeText(
                    this, "Please give country code and phone number",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

     fun sendOTP(code: String, phone_number:String) {

        if (checkConnection()) {
            sendOtpPresenter?.attemptSendOTP(code,phone_number)
        } else Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()

    }

    override fun onSuccess(sendOTP: SendOTP?, code: Int) {
        CuteToast.ct(this, sendOTP?.message, CuteToast.LENGTH_LONG, CuteToast.SUCCESS, true).show();
        val code: String = _binding?.codeEt?.text.toString()
        val phone_number: String = _binding?.phoneEt?.text.toString()
        val intent = Intent(this,OTPActivity::class.java)
        intent.putExtra("country_code",code)
        intent.putExtra("phone_number",phone_number)
        startActivity(intent)
    }

    override fun onError(error: String?, code: Int) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    private fun checkConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }
}