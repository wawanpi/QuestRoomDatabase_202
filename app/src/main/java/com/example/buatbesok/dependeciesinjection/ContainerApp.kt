package com.example.buatbesok.dependeciesinjection

import android.content.Context
import com.example.buatbesok.data.database.KrsDatabase
import com.example.buatbesok.repository.LocalRepositoryMhs
import com.example.buatbesok.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp (private val context: Context) : InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.geDtabase(context).mahasiswaDao())
    }
}