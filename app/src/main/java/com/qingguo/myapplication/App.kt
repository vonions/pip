package com.qingguo.myapplication

import android.app.Application
import android.content.Context

class App :Application(){


    companion  object {
        private lateinit var context:Context
    }
    override fun onCreate() {
        super.onCreate()
        context=this
    }
}