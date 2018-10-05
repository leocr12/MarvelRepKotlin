package com.example.leonardoallen.marvelrepkotlin.comic.model

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.TypeConverters
import com.example.leonardoallen.marvelrepkotlin.util.Prices
import com.example.leonardoallen.marvelrepkotlin.character.model.Character
import com.example.leonardoallen.marvelrepkotlin.comic.util.ComicThumbnail
import com.example.leonardoallen.marvelrepkotlin.comic.util.ComicTypeConverters

@Entity(tableName = "comic", primaryKeys = ["id"], foreignKeys = arrayOf(ForeignKey(entity = Character::class, parentColumns = arrayOf("id"), childColumns = arrayOf("characterId"))))
class Comic(val id: Int?, val title: String, val description: String, @field:TypeConverters(ComicTypeConverters::class) val prices: List<Prices>,
            @field:Embedded val thumbnail: ComicThumbnail) {
    var characterId: Int = 0
}