package com.example.leonardoallen.marvelrepkotlin.comic.util

import com.google.gson.annotations.SerializedName

class ComicThumbnail(@field:SerializedName("extension")
                     val extension: String) {
    @SerializedName("path")
    var path: String? = null

    val imageUrl: String
        get() = "$path.$extension"

}