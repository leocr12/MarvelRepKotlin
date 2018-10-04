package com.example.leonardoallen.marvelrepkotlin

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup

abstract class BaseActivity : AppCompatActivity() {
    val viewGroup: ViewGroup
        get() = findViewById<View>(android.R.id.content) as ViewGroup
}