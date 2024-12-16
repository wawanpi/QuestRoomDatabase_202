package com.example.buatbesok.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.buatbesok.repository.RepositoryMhs
import com.example.buatbesok.ui.navigation.DestinasiDetail
import kotlinx.coroutines.flow.StateFlow

class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel()




data class DetailUiState(
    val detaiUiEvent :MahasiswaEvent = MahasiswaEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val erorMessage: String = ""
)