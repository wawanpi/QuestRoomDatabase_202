package com.example.buatbesok.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.buatbesok.data.entity.Mahasiswa
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
){
    val isUiEventEmpty: Boolean
        get() = detaiUiEvent == MahasiswaEvent()

    val isEventNotEmpety: Boolean
        get() = detaiUiEvent != MahasiswaEvent()

}
/* Data class untuk menampung data yang akan ditampilkan di UI*/

//memindahkan data dari entity ke ui
fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent{
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
    )
}