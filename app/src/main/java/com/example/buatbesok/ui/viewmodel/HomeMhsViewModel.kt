package com.example.buatbesok.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.buatbesok.data.entity.Mahasiswa
import com.example.buatbesok.repository.RepositoryMhs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.internal.NopCollector.emit

class HomeMhsViewModel(
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = repositoryMhs.getAllMhs()
        .filterNOtNull()
        .map {
            HomeUiState(
                listMhs = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiState(isLoading = true))
            delay(900)
        }
}

data class HomeUiState(
    val listMhs: List<Mahasiswa> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = ""
)