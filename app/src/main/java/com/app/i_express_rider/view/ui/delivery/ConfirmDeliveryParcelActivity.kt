package com.app.i_express_rider.view.ui.delivery

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.databinding.ActivityConfirmDeliveryBinding

class ConfirmDeliveryParcelActivity : AppCompatActivity() {

    companion object {
        var TAG = "ConfirmDeliveryParcelActivity"
    }

    lateinit var binding: ActivityConfirmDeliveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmDeliveryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnConfirm.setOnClickListener {
            Log.d(TAG, "otpView onclick")
            Toast.makeText(this, "Successfully delivered", Toast.LENGTH_SHORT).show()
            onBackPressed()
        }

    }
}