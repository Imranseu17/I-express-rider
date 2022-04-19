package com.app.i_express_rider.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityAddressBinding
import com.app.i_express_rider.databinding.ActivityProfileBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.next.setOnClickListener {
            startActivity(Intent(this,SocialMediaActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}