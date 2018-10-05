package com.example.leonardoallen.marvelrepkotlin.character.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.leonardoallen.marvelrepkotlin.character.model.Character

@Dao
interface CharacterDao {

    @get:Query("SELECT * FROM Character")
    val allCharacters: MutableList<Character>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(results: MutableList<Character>)
}
