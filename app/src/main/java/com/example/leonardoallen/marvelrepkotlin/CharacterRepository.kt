package com.example.leonardoallen.marvelrepkotlin

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class CharacterRepository {

    private var cache: CharactersResponse? = null

    fun fetchCharacters(): Observable<CharactersResponse> {
        if (cache != null) {
            Observable.just(cache)
        }
        return Injector.marvelApi().characters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { cache = it }
    }

    internal fun fetchMoreCharacters(offset: Int): Observable<CharactersResponse> {
        if (cache != null) {
            Observable.just(cache)
        }
        return Injector.marvelApi().getMoreCharacters(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { cache = it }
    }
}