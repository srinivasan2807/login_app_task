package com.example.loginapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DbModel.RegisterUser::class], version = 1, exportSchema = false)
abstract class LoginDatabase : RoomDatabase() {
    companion object {
        var instance: LoginDatabase? = null
        fun getInstance(context: Context): LoginDatabase {
            if (instance != null) {
                return instance as LoginDatabase
            }
            instance = Room.databaseBuilder(context, LoginDatabase::class.java, "usersDb").run {
                allowMainThreadQueries()
            }.build()
            return instance as LoginDatabase
        }
    }

    abstract fun registerDao(): RegisterDao
}