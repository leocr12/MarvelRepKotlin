package com.example.leonardoallen.marvelrepkotlin.util

import com.google.gson.annotations.SerializedName

class Prices(@field:SerializedName("type")
             val type: String, @field:SerializedName("price")
             val price: Double)