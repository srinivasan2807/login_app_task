package com.example.loginapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.db.DbModel
import com.example.loginapp.db.LoginDatabase
import com.example.loginapp.db.RegisterDao
import com.example.loginapp.utils.CommonUtils
import com.example.loginapp.utils.PermissionUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    var userName: String? = null
    var password: String? = null
    var dob: String? = null
    var mobileNumber: Int = 0
    private var email: String? = null
    var otp: Int = 0
    private var profilePicture: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        et_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    userName = s.toString()
                } else {
                    Toast.makeText(this@RegisterActivity, "check username", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        et_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    password = s.toString()
                } else {
                    Toast.makeText(this@RegisterActivity, "check password", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        })
        et_dob.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    dob = s.toString()
                } else {
                    Toast.makeText(this@RegisterActivity, "check dob", Toast.LENGTH_SHORT).show()
                }
            }
        })
        et_mobile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isNotEmpty()) {
                    if (s.toString().length == 10) {
                        mobileNumber = s.toString().toInt()
                    } else {
                        Toast.makeText(
                            this@RegisterActivity, "check mobile number length", Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(this@RegisterActivity, "check mobile number", Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        et_otp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (mobileNumber != 0) {
                    val randomOTP = (Math.random() * 9000).toInt() + 1000
                    et_otp.setText(randomOTP.toString())
                    otp = et_otp.toString().trim().toInt()
                } else {
                    Toast.makeText(this@RegisterActivity, "check mobile number", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        et_email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isNotEmpty()) {
                    dob = s.toString()
                } else {
                    Toast.makeText(this@RegisterActivity, "check email", Toast.LENGTH_SHORT).show()
                }
            }

        })
        profile_pic.setEndIconOnClickListener {
            if (PermissionUtils.checkPermissions(this@RegisterActivity) == true) {
                openImagePicker()
            } else {
                PermissionUtils.checkPermissions(this@RegisterActivity)
            }
        }
        signup.setOnClickListener {

            val userDetail = DbModel.RegisterUser(
                userName = userName.toString(),
                password = password.toString(),
                dateOfBirth = dob.toString(),
                profilePic = profilePicture.toString(),
                mobile = mobileNumber,
                email = email.toString(),
                sessionStart = CommonUtils.getCurrentDateTime(),
                sessionEnd = "",
                sessionDuration = ""
            )
           LoginDatabase.getInstance(this@RegisterActivity).registerDao().addUser(userDetail)
            startActivity(Intent(this@RegisterActivity,MainActivity::class.java))
        }
    }

    private fun openImagePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (PermissionUtils.isPhotoPickerAvailable()) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        } else {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            imagePickerResult.launch(intent)
        }

    }

    private val imagePickerResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = intent.data
                imageUri?.let {
                    val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    this@RegisterActivity.contentResolver.takePersistableUriPermission(it, flag)
                    profilePicture = it.toString()
                }

            }
        }
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                this@RegisterActivity.contentResolver.takePersistableUriPermission(imageUri, flag)
                profilePicture = imageUri.toString()
            }
        }
}