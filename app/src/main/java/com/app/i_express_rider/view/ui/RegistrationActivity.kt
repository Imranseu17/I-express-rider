package com.app.i_express_rider.view.ui


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.i_express_rider.Model.Presenter.RegistrationPresenter
import com.app.i_express_rider.Model.callback.RegistrationUserView
import com.app.i_express_rider.Model.models.Registration
import com.app.i_express_rider.R
import java.text.SimpleDateFormat
import java.util.*


class RegistrationActivity : AppCompatActivity(),RegistrationUserView {


    private var currentApiVersion = 0
    val myCalendar = Calendar.getInstance()
    var editTextCalender: EditText? = null
    var registrationPresenter:RegistrationPresenter? = null


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

        registrationPresenter = RegistrationPresenter(this)

        findViewById<LinearLayout>(R.id.save).setOnClickListener {

            var email:String = findViewById<EditText>(R.id.emailEt).text.toString().trim()
            var country_code:String? = intent.getStringExtra("country_code")
            var phone_number:String? = intent.getStringExtra("phone_number")
            var password:String = findViewById<EditText>(R.id.passEt).text.toString().trim()
            var again_password:String = findViewById<EditText>(R.id.AgainPassEt).text.toString().trim()
            var givenname:String = findViewById<EditText>(R.id.first_nameEt).text.toString().trim()
            var middlename:String = findViewById<EditText>(R.id.middle_nameEt).text.toString().trim()
            var surname:String = findViewById<EditText>(R.id.last_nameEt).text.toString().trim()
            var dob:String? = findViewById<EditText>(R.id.birthdayEt).text.toString().trim()
            var appId:String? = "APP-I2345"
            var ipAddress:String? = "192.168.0.118"
            var verifyToken:String? = intent.getStringExtra("verify_token")
            var autoLogin = true

            if(!validateEmail())
                return@setOnClickListener
            if(!validatePassword())
                return@setOnClickListener
            if(!validateAgainPass())
                return@setOnClickListener
            if(!validateFirstName())
                return@setOnClickListener
            if(!validateLastName())
                return@setOnClickListener
            if(!validateMiddleName())
                return@setOnClickListener
            if(!validateBirthDate())
                return@setOnClickListener

            if(password.equals(again_password)){
                registration(email,country_code,phone_number,password,givenname,middlename,
                    surname,dob,appId,ipAddress,verifyToken,autoLogin)
            }else{

                Toast.makeText(this,"Password and Confirm password will be same",
                    Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun registration(email:String , country_code:String? ,phone:String?,
                     password:String, givenname:String,middlename:String,
                     surname:String,dob:String?,appId:String?,ipAddress:String?,
                     verifyToken:String? ,autoLogin:Boolean){

        if (checkConnection()) {
            registrationPresenter?.
            attemptRegistration(email, country_code, phone, password, givenname, middlename,surname,
                dob, appId, ipAddress, verifyToken, autoLogin)
        } else Toast.makeText(this,"No internet connection",Toast.LENGTH_LONG).show()

    }




    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        editTextCalender?.setText(dateFormat.format(myCalendar.time))
    }

    override fun onSuccess(registration: Registration?, code: Int) {
        Toast.makeText(this,registration?.message,Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this@RegistrationActivity,
                MainActivity::class.java
            )
        )
    }

    override fun onError(error: String?, code: Int) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

    private fun checkConnection(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    private fun validateEmail(): Boolean {
        val email: String = findViewById<EditText>(R.id.emailEt).getText().toString().trim()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Email Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validatePassword(): Boolean {
        val password: String = findViewById<EditText>(R.id.passEt).getText().toString().trim()
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"password Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validateAgainPass(): Boolean {
        val againPassword: String = findViewById<EditText>(R.id.AgainPassEt).getText().toString().trim()
        if (TextUtils.isEmpty(againPassword)) {
            Toast.makeText(this,"Confirm password Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validateFirstName(): Boolean {
        val firstName: String = findViewById<EditText>(R.id.first_nameEt).getText().toString().trim()
        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(this,"First Name Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validateLastName(): Boolean {
        val lastName: String = findViewById<EditText>(R.id.last_nameEt).getText().toString().trim()
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(this,"Last Name Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validateMiddleName(): Boolean {
        val middlename: String = findViewById<EditText>(R.id.middle_nameEt).getText().toString().trim()
        if (TextUtils.isEmpty(middlename)) {
            Toast.makeText(this,"Middle Name Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
           // Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }
    private fun validateBirthDate(): Boolean {
        val birthDate: String = findViewById<EditText>(R.id.birthdayEt).getText().toString().trim()
        if (TextUtils.isEmpty(birthDate)) {
            Toast.makeText(this,"Birth Date Filed is required",Toast.LENGTH_SHORT).show()
            return false
        }  else {
          //  Toast.makeText(this,"no Error",Toast.LENGTH_SHORT).show()
        }
        return true
    }


}