package com.challenge.demo.tmbd.tmbdmoviesapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun toSimpleString(date: Date) : String {
        val format = SimpleDateFormat("dd-MMMM-yyy",Locale.getDefault())
        return format.format(date)
    }
}