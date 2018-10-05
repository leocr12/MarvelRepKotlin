package com.example.leonardoallen.marvelrepkotlin.util

import android.os.Parcel
import android.os.Parcelable

class Thumbnail : Parcelable {

    var extension: String? = null
        private set
    var path: String? = null

    val imageUrl: String
        get() = "$path.$extension"

    constructor(extension: String) {
        this.extension = extension
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.extension)
        dest.writeString(this.path)
    }

    private constructor(`in`: Parcel) {
        this.extension = `in`.readString()
        this.path = `in`.readString()
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Thumbnail> = object : Parcelable.Creator<Thumbnail> {
            override fun createFromParcel(source: Parcel): Thumbnail {
                return Thumbnail(source)
            }

            override fun newArray(size: Int): Array<Thumbnail?> {
                return arrayOfNulls(size)
            }
        }
    }
}