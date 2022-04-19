package com.app.i_express_rider.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityAddRelativeBinding
import com.app.i_express_rider.databinding.ActivityFamilyInfoBinding
import com.app.i_express_rider.databinding.ActivitySocialMediaBinding
import com.google.android.material.snackbar.Snackbar

class FamilyInfoAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFamilyInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamilyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val spinner = binding.maritalStatusSpinner
        spinner.setItems("Select Gender","Male", "Female", "Transgender", "Others")
        spinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }
        binding.next.setOnClickListener {
            startActivity(Intent(this,AddRelativeActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}