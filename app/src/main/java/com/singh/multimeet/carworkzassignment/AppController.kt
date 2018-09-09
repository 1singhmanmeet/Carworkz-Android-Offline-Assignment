package com.singh.multimeet.carworkzassignment

import android.app.Application
import com.androidnetworking.AndroidNetworking

public class AppController: Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

}