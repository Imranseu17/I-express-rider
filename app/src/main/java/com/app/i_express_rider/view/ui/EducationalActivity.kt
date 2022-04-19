package com.app.i_express_rider.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.i_express_rider.R
import com.app.i_express_rider.databinding.ActivityAddRelativeBinding
import com.app.i_express_rider.databinding.ActivityEducationalBinding
import com.google.android.material.snackbar.Snackbar

class EducationalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEducationalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEducationalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val psc5PassSpinner = binding.psc5PassSpinner
        psc5PassSpinner.setItems("Exam/Degree Title","PSC", "Ebtedayee(Madrasah)",
                        "5 Pass","Other n")
        psc5PassSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val jscJdc8PassSpinner = binding.jscJdc8PassSpinner
        jscJdc8PassSpinner.setItems("Exam/Degree Title","JSC", "JDC(Madrasah)",
            "8 Pass","Other")
        jscJdc8PassSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val secondaryTypeSpinner = binding.secondaryTypeSpinner
        secondaryTypeSpinner.setItems("Exam/Degree Title","SSC", "O Level", "Dakhil(Madrasah)",
            "SSC(Vocational)","Other")
        secondaryTypeSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val higherSecondaryTypeSpinner = binding.higherSecondaryTypeSpinner
        higherSecondaryTypeSpinner.setItems("Exam/Degree Title","HSC", "A Level", "Alim(Madrasah)",
            "HSC(Vocational)","Other")
        higherSecondaryTypeSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }



        val diplomaSpinner = binding.diplomaSpinner
        diplomaSpinner.setItems("Exam/Degree Title","Concentration",
            "Major","Group")
        diplomaSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val bachelorHonorsSpinner = binding.bachelorHonorsSpinner
        bachelorHonorsSpinner.setItems("Exam/Degree Title","Concentration",
            "Major","Group")
        bachelorHonorsSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val mastersSpinner = binding.mastersSpinner
        mastersSpinner.setItems("Exam/Degree Title","Concentration",
            "Major","Group")
        mastersSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        val phdSpinner = binding.phdSpinner
        phdSpinner.setItems("Exam/Degree Title","Concentration",
            "Major","Group")
        phdSpinner.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(
                view,
                "Clicked $item",
                Snackbar.LENGTH_LONG
            ).show()
        }

        binding.next.setOnClickListener {
            startActivity(Intent(this,
                TrainingAndCertificationActivity::class.java))
        }

        binding.toolbar.setOnClickListener { finish() }
    }
}