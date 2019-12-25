package com.example.opengldemo

import android.app.Application

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("native-lib")
    }
}