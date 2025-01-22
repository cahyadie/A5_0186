package com.example.ujianakhir

import android.app.Application
import com.example.ujianakhir.repository.AppContainer
import com.example.ujianakhir.repository.Container

class App: Application()  {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = Container()
    }
}