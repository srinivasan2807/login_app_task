package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginapp.db.LoginDatabase
import com.example.loginapp.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = LoginDatabase.getInstance(this@MainActivity).registerDao()
        val userDetails = db.fetchUser()
        logged_in_success.text = userDetails.userName.plus(" ").plus("datetime:${userDetails.sessionStart}")
        btn_logout.setOnClickListener {
            db.updateUser(sessionEnd = CommonUtils.getCurrentDateTime(),userDetails.id)
            db.updateUserDuration(sessionDuration = CommonUtils.calculateLoginTimeDuration(userDetails.sessionStart,userDetails.sessionEnd),userDetails.id)
            db.clearSession(userDetails.id)
            startActivity(Intent(this@MainActivity,RegisterActivity::class.java))
        }
    }
}