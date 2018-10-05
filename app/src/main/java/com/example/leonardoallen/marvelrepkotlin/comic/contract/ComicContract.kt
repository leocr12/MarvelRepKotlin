package com.example.leonardoallen.marvelrepkotlin.comic.contract

import com.example.leonardoallen.marvelrepkotlin.comic.api.ComicResponse
import com.example.leonardoallen.marvelrepkotlin.comic.model.Comic

interface ComicContract {

    interface View {
        fun hideProgressBar()
        fun showEmptyComicError()
        fun loadFirstPage(comics: MutableList<Comic>, total: Int, count: Int)
        fun loadNextPage(comics: MutableList<Comic>, count: Int)
        fun setComicData(comics: MutableList<Comic>)
        fun addOnScrollListenerRecyclerView()
        fun setCharacterId(comics: MutableList<Comic>, characterId: Int)
    }

    interface Presenter {
        fun init(characterId: Int)
        fun loadMoreComics(offset: Int)
        fun retrieveComics(characterId: Int)
        fun retrieveAllComics(characterId: Int): MutableList<Comic>
        fun retrieveComicsFromDatabase(characterId: Int)
        fun insertComicsIntoDatabase(comics: MutableList<Comic>)
        fun insertComicsIntoDatabase(comicResponse: ComicResponse)
        fun retrieveDatabase()
    }
}
