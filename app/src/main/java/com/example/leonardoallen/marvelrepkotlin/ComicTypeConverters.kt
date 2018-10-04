package com.example.leonardoallen.marvelrepkotlin

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ComicTypeConverters {

    private var gson = Gson()

    @TypeConverter
    fun PricesList(data: String?): List<Prices> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<Prices>>() {

        }.type

        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun PricesListToString(prices: List<Prices>): String {
        return gson.toJson(prices)
    }
}