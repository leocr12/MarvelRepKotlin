package com.example.leonardoallen.marvelrepkotlin

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Character::class), exportSchema = false, version = 1)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun marvelDao(): CharacterDao

}