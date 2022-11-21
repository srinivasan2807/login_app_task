package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.loginapp.db.LoginDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_otp
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {
    var mobileNumber:Int =0
    var otp:Int = 0
    val db = LoginDatabase.getInstance(this@LoginActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

       et_mobile_number.addTextChangedListener(object :TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
           }

           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
              if (s.toString().trim().isNotEmpty() && s.toString().length==10){
                  mobileNumber = s.toString().toInt()
              }else{
                  Toast.makeText(this@LoginActivity, "Please enter valid mobile number", Toast.LENGTH_SHORT).show()
              }
           }

           override fun afterTextChanged(s: Editable?) {

           }

       })
        et_otp.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val randomOTP = (Math.random() * 9000).toInt() + 1000
                et_otp.setText(randomOTP.toString())
                otp = et_otp.toString().toInt()
                db.registerDao().updateOtp(otp,mobileNumber)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        btn_signin.setOnClickListener {
            if (mobileNumber!=0 && otp!=0){
               val userList = db.registerDao().checkLogin(mobileNumber,otp)
                if (userList.isNullOrEmpty()){
                    startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                }
            }
        }
        bt_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
        }
    }
}