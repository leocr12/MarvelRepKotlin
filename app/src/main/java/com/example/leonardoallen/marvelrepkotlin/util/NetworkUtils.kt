package com.example.leonardoallen.marvelrepkotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.leonardoallen.marvelrepkotlin.MarvelApplication

class NetworkUtils private constructor() {

    // we do not use it very often, make it lazy
    val isConnected: Boolean
        get() {
            if (networkInfo == null) {
                networkInfo = retrieveNetworkInfo()
            }
            return networkInfo != null && networkInfo!!.isConnectedOrConnecting
        }
    private var networkInfo: NetworkInfo? = null

    private fun retrieveNetworkInfo(): NetworkInfo {
        val connectivityManager = MarvelApplication.context.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.activeNetworkInfo
    }

    companion object {

        fun getInstance(): NetworkUtils {
            if (instance == null) {
                instance = NetworkUtils()
            }
            return instance!!
        }

        private var instance: NetworkUtils? = null
    }
}