package com.muhammedsafiulazam.githublister

import android.app.Application

/**
 * Created by Muhammed Safiul Azam on 24/07/2019.
 */

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Knowledge.initialize(this)
    }
}