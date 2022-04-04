package com.app.i_express_rider.view.ui

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.R
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import java.text.SimpleDateFormat
import java.util.*

class RegistrationActivity : AppCompatActivity() {


    private var currentApiVersion = 0
    private var spProvince: SmartMaterialSpinner<String>? = null
    private var provinceList: MutableList<String>? = null
    val myCalendar = Calendar.getInstance()
    var editTextCalender: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_registration)
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
           findViewById<ImageButton>(R.id.back_button) .setOnClickListener(View.OnClickListener {
             finish()
        })

        editTextCalender = findViewById(R.id.birthdayEt)

        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }

       editTextCalender?.setOnClickListener {
           DatePickerDialog(
               this@RegistrationActivity, date, myCalendar.get(Calendar.YEAR),
               myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
           ).show()
       }

        findViewById<LinearLayout>(R.id.save).setOnClickListener {
            startActivity(
                Intent(
                    this@RegistrationActivity,
                    MainActivity::class.java
                )
            )
        }



        initSpinner()


    }

    fun initSpinner() {
        spProvince = findViewById(R.id.spinner)

        provinceList = ArrayList()

        provinceList?.add("Male")
        provinceList?.add("Female")
        provinceList?.add("Others")


        spProvince?.item = provinceList

        spProvince?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
              //  Toast.makeText(this@RegistrationActivity, provinceList!![position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editTextCalender?.setText(dateFormat.format(myCalendar.time))
    }

}