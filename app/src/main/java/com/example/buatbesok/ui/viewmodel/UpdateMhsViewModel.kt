package com.example.buatbesok.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buatbesok.repository.RepositoryMhs
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {
    var updateUiState by mutableStateOf(MhsUiState())
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch{
            updateUiState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }

        fun updateState(mahasiswaEvent: MahasiswaEvent) {
            updateUiState = updateUiState.copy(
                mahasiswaEvent = mahasiswaEvent,
            )
        }
        fun validataFields(): Boolean {
            val event = updateUiState.mahasiswaEvent
            val errorState = FormErrorState(
                nim = if (event.nim.isNotEmpty()) null else "Nim tidak boleh kosong",
                nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
                jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
                alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
                kelas = if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
                angkatan = if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"
            )
            updateUiState = updateUiState.copy(isEntryValid = errorState)
            return errorState.isValid()
        }

    }

}

