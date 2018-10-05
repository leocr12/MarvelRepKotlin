package com.example.leonardoallen.marvelrepkotlin.character.contract

import com.example.leonardoallen.marvelrepkotlin.character.model.Character

interface CharacterContract {

    interface View {
        fun hideProgressBar()
        fun showNetworkError()
        fun addOnScrollListenerRecyclerView()
        fun loadFirstPage(characters: MutableList<Character>, count: Int, total: Int)
        fun loadNextPage(characters: MutableList<Character>, count: Int)
        fun setCharacterData(characters: MutableList<Character>)
    }

    interface Presenter {
        fun init()
        fun loadMoreCharacters(offset: Int)
        fun retrieveCharacters()
        fun retrieveAllCharactersFromDB()
        fun insertCharactersIntoDatabase(characters: MutableList<Character>)
        fun retrieveAllCharacters(): MutableList<Character>
        fun retrieveDatabase()
    }
}