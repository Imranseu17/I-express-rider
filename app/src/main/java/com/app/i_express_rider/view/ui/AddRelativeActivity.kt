package com.app.i_express_rider.view.ui


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.databinding.ActivityAddRelativeBinding
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner


class AddRelativeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddRelativeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRelativeBinding.inflate(layoutInflater)
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
            startActivity(Intent(this,EducationalActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}