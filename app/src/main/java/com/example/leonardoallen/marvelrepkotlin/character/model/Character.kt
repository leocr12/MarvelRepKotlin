package com.example.leonardoallen.marvelrepkotlin.character.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.os.Parcel
import android.os.Parcelable
import com.example.leonardoallen.marvelrepkotlin.util.Thumbnail

@Entity(tableName = "character", primaryKeys = ["id"])
class Character : Parcelable {

    var id: Int = 0
        private set
    var name: String? = null
        private set
    var description: String? = null
        private set
    @Embedded(prefix = "thumbnail_")
    val thumbnail: Thumbnail

    constructor(id: Int, name: String, description: String, thumbnail: Thumbnail) {
        this.id = id
        this.name = name
        this.description = description
        this.thumbnail = thumbnail
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(this.id)
        dest.writeString(this.name)
        dest.writeString(this.description)
        dest.writeParcelable(this.thumbnail, flags)
    }

    private constructor(`in`: Parcel) {
        this.id = `in`.readInt()
        this.name = `in`.readString()
        this.description = `in`.readString()
        this.thumbnail = `in`.readParcelable(Thumbnail::class.java.classLoader)
    }

    companion object {

        @JvmField
        val CREATOR: Parcelable.Creator<Character> = object : Parcelable.Creator<Character> {
            override fun createFromParcel(source: Parcel): Character {
                return Character(source)
            }

            override fun newArray(size: Int): Array<Character?> {
                return arrayOfNulls(size)
            }
        }
    }
}