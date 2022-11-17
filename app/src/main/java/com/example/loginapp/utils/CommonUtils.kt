package com.example.loginapp.utils

import java.text.SimpleDateFormat
import java.util.*

object CommonUtils {
    fun getCurrentDateTime(): String {
        val c: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.getDefault())
        return simpleDateFormat.format(c.time)
    }
    fun calculateLoginTimeDuration(sessionStart:String,sessionEnd:String): String {
        val start = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.getDefault()).parse(sessionStart)
        val end = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aa", Locale.getDefault()).parse(sessionEnd)
        var difference = end.time - start.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays: Long = difference / daysInMilli
        difference %= daysInMilli

        val elapsedHours: Long = difference / hoursInMilli
        difference %= hoursInMilli

        val elapsedMinutes: Long = difference / minutesInMilli
        difference %= minutesInMilli

        val elapsedSeconds: Long = difference / secondsInMilli
        return "$elapsedDays".plus("days").plus(" ").plus("$elapsedHours").plus("Hours").plus(" ").plus("$elapsedMinutes").plus("minutes").plus(" ").plus("$elapsedSeconds").plus("seconds")
    }
}