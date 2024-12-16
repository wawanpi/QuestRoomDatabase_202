package com.example.buatbesok.ui.viewmodel

import com.example.buatbesok.data.entity.Mahasiswa
import com.example.buatbesok.repository.RepositoryMhs

class HomeMhsViewModel(
    private val repositoryMhs: RepositoryMhs
)

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = ""
)