package com.example.leonardoallen.marvelrepkotlin

class DatabaseHelper {

    var marvelDatabase = Injector.provideDatabase()

    val allCharacters: List<Character>
        get() = marvelDatabase.marvelDao().allCharacters

    fun insertCharacters(characters: List<Character>) {
        marvelDatabase.marvelDao().insert(characters)
    }

    fun insertComics(comics: List<Comic>) {
        marvelDatabase.comicDao().insert(comics)
    }

    fun isComicEmpty(characterId: Int): Boolean {
        return marvelDatabase.comicDao().getComics(characterId).isEmpty()
    }

    fun getComics(characterId: Int): List<Comic> {
        return marvelDatabase.comicDao().getComics(characterId)
    }

}