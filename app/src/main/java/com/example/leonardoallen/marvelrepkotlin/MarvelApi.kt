package com.example.leonardoallen.marvelrepkotlin

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface MarvelApi {

    @GET("characters?$authentication")
    fun characters(): Observable<CharactersResponse>

    @GET("characters/{characterId}/comics?$authentication")
    fun character(
            @Path("characterId") characterId: Int): Observable<ComicResponse>

    @GET("characters?$authentication")
    fun getMoreCharacters(
            @Query("offset") offset: Int): Observable<CharactersResponse>

    @GET("characters/{characterId}/comics?$authentication")
    fun getMoreComics(@Path("characterId") characterId: Int,
                      @Query("offset") offset: Int): Observable<ComicResponse>

    companion object {

        const val API_KEY = BuildConfig.MarvelPublicKey
        const val HASH_KEY = BuildConfig.MarvelHashKey
        const val timestamp = "1"

        const val authentication = "hash=$HASH_KEY&apikey=$API_KEY&ts=$timestamp"
    }
}