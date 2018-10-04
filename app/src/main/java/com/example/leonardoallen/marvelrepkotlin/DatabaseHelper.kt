package com.example.leonardoallen.marvelrepkotlin

class DatabaseHelper {

    var marvelDatabase = Injector.provideDatabase()

    val allCharacters: MutableList<Character>
        get() = marvelDatabase.marvelDao().allCharacters

    fun insertCharacters(characters: MutableList<Character>) {
        marvelDatabase.marvelDao().insert(characters)
    }

}