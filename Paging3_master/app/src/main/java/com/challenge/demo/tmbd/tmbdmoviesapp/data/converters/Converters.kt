package com.challenge.demo.tmbd.tmbdmoviesapp.data.converters

import androidx.room.TypeConverter
import com.challenge.demo.tmbd.tmbdmoviesapp.data.model.Image
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromImage(image: Image?): String? {
        return image?.url
    }

    @TypeConverter
    fun toImage(url: String?): Image? {
        return url?.let { Image(it) }
    }
}