package com.example.buatbesok.repository

import com.example.buatbesok.data.entity.Mahasiswa

interface RepositoryMhs {

    suspend fun insertMhs(mahasiswa: Mahasiswa)
}