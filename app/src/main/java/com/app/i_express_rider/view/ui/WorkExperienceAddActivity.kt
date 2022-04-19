package com.app.i_express_rider.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityTrainingAndCertificationBinding
import com.app.i_express_rider.databinding.ActivityWorkExperienceAddBinding

class WorkExperienceAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkExperienceAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkExperienceAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.next.setOnClickListener {
            startActivity(
                Intent(this,
                    DocumentsAddActivity::class.java)
            )
        }

        binding.toolbar.setOnClickListener { finish() }

    }
}