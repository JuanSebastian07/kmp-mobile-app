package org.composemultiplatform

import android.app.Application
import android.util.Log
import org.composemultiplatform.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        try {
            initKoin {
                androidContext(this@MyApplication)
                androidLogger()
            }
        }catch (e: Exception) {
            Log.e("KOIN", "Error initializing Koin", e)
        }

    }
}