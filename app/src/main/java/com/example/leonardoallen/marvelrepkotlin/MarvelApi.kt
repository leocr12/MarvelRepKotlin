package com.example.leonardoallen.marvelrepkotlin

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface MarvelApi {

    @GET("characters?$authentication")
    fun characters(): Observable<CharactersResponse>

    @GET("characters?$authentication")
    fun getMoreCharacters(@Query("offset") offset: Int): Observable<CharactersResponse>

    companion object {

        private const val API_KEY = BuildConfig.MarvelPublicKey
        private const val HASH_KEY = BuildConfig.MarvelHashKey
        private const val timestamp = "1"

        const val authentication = "hash=$HASH_KEY&apikey=$API_KEY&ts=$timestamp"
    }
}