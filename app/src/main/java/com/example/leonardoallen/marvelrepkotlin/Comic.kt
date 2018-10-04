package com.example.leonardoallen.marvelrepkotlin

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.TypeConverters

@Entity(tableName = "comic", primaryKeys = ["id"], foreignKeys = arrayOf(ForeignKey(entity = Character::class, parentColumns = arrayOf("id"), childColumns = arrayOf("characterId"))))
class Comic(val id: Int?, val title: String, val description: String, @field:TypeConverters(ComicTypeConverters::class) val prices: List<Prices>,
            @field:Embedded val thumbnail: ComicThumbnail) {
    var characterId: Int = 0
}