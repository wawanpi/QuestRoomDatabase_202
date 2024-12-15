package com.example.buatbesok

import android.app.Application
import com.example.buatbesok.dependeciesinjection.ContainerApp

class KrsApp : Application()  {
    lateinit var containerApp: ContainerApp //Fungsinya untuk menyimpan

    override fun onCreate() {
        super.onCreate()
        containerApp = ContainerApp(this) //Membuat instance dari ContainerApp
    }   //instance adalah membuat object yang dibuat dari class

}