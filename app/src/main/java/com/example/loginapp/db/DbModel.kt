package com.example.loginapp.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

class DbModel {
    @Entity(tableName = "tableregister")
    data class RegisterUser(
        @ColumnInfo(name = "username") var userName: String,
        @ColumnInfo(name = "password") var password: String,
        @ColumnInfo(name = "dob") var dateOfBirth: String,
        @ColumnInfo(name = "profilepic") var profilePic: String,
        @ColumnInfo(name = "mobile") var mobile: Int,
        @ColumnInfo(name = "otp") var otp: Int,
        @ColumnInfo(name = "email") var email: String,
        @ColumnInfo(name = "sessionstart") var sessionStart: String,
        @ColumnInfo(name = "sessionend") var sessionEnd: String,
        @ColumnInfo(name = "sessionduration") var sessionDuration: String,
    ) {
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0
    }

}

