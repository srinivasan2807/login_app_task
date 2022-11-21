package com.example.loginapp.db

import androidx.room.*

@Dao
interface RegisterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(registerUser: DbModel.RegisterUser)

    @Query("select * from tableregister where mobile =:mobile and otp=:otp")
    fun checkLogin(mobile: Int, otp: Int): MutableList<DbModel.RegisterUser>

    @Query("select * from tableregister")
    fun fetchUser(): DbModel.RegisterUser

    @Query("update tableregister SET sessionend = :sessionEnd where id = :userId")
    fun updateUser(sessionEnd: String, userId: Int): DbModel.RegisterUser

    @Query("update tableregister SET otp  = :otp where mobile = :mobileNumber")
    fun updateOtp(otp: Int, mobileNumber: Int): DbModel.RegisterUser

    @Query("update tableregister SET sessionduration = :sessionDuration where id = :userId")
    fun updateUserDuration(sessionDuration: String, userId: Int): DbModel.RegisterUser

    @Query("update tableregister SET sessionstart = null ,sessionend =null where id=:userId")
    fun clearSession(userId: Int): Int
}