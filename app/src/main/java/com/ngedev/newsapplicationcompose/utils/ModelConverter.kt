package com.ngedev.newsapplicationcompose.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ngedev.newsapplicationcompose.domain.model.Source

class ModelConverter {
    @TypeConverter
    fun toSourceModel(json: String): Source {
        val type = object : TypeToken<Source>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(torrent: Source): String {
        val type = object : TypeToken<Source>() {}.type
        return Gson().toJson(torrent, type)
    }


}