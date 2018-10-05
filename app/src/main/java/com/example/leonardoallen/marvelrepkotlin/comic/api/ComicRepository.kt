package com.example.leonardoallen.marvelrepkotlin.comic.api

import com.example.leonardoallen.marvelrepkotlin.di.Injector
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ComicRepository {

    private var cache: ComicResponse? = null

    fun fetchComics(characterId: Int): Observable<ComicResponse> {
        if (cache != null) {
            Observable.just(cache)
        }
        return Injector.marvelApi().character(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { cache = it }
    }

    fun fetchMoreComics(characterId: Int, offset: Int): Observable<ComicResponse> {
        if (cache != null) {
            Observable.just(cache)
        }
        return Injector.marvelApi().getMoreComics(characterId, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { cache = it }
    }

}
