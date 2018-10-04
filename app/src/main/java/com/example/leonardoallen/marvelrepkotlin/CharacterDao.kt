package com.example.leonardoallen.marvelrepkotlin

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CharacterDao {

    @get:Query("SELECT * FROM Character")
    val allCharacters: MutableList<Character>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(results: MutableList<Character>)
}
