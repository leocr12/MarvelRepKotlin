package com.example.leonardoallen.marvelrepkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.leonardoallen.marvelrepkotlin.character.model.Character
import com.example.leonardoallen.marvelrepkotlin.character.database.CharacterDao
import com.example.leonardoallen.marvelrepkotlin.comic.model.Comic
import com.example.leonardoallen.marvelrepkotlin.comic.database.ComicDao

@Database(entities = arrayOf(Character::class, Comic::class), exportSchema = false, version = 1)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun marvelDao(): CharacterDao
    abstract fun comicDao(): ComicDao

}