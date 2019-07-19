package com.example.feed.database

import android.arch.persistence.room.TypeConverter
import com.example.feed.datamodel.FeedDetailsModel
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: Array<FeedDetailsModel>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): Array<FeedDetailsModel>? {
        val objects = Gson().fromJson(value, Array<FeedDetailsModel>::class.java) as Array<FeedDetailsModel>
        //val list = objects.toList() as Array<FeedDetailsModel>
        return objects
    }
}