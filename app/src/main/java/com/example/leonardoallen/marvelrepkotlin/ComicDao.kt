package com.example.leonardoallen.marvelrepkotlin

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ComicDao {

    @Query("SELECT * FROM comic WHERE characterId = :characterId")
    fun getComics(characterId: Int): MutableList<Comic>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(comics: MutableList<Comic>)
}