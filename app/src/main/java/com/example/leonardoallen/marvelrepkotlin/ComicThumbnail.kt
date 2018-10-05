package com.example.leonardoallen.marvelrepkotlin

import com.google.gson.annotations.SerializedName

class ComicThumbnail(@field:SerializedName("extension")
                     val extension: String) {
    @SerializedName("path")
    var path: String? = null

    val imageUrl: String
        get() = "$path.$extension"

}