package com.app.i_express_rider.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityAddressBinding
import com.app.i_express_rider.databinding.ActivitySocialMediaBinding

class SocialMediaActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySocialMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySocialMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.next.setOnClickListener {
            startActivity(Intent(this,FamilyInfoAddActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}