package com.example.buatbesok.ui.viewmodel



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buatbesok.data.entity.Mahasiswa
import com.example.buatbesok.repository.RepositoryMhs
import kotlinx.coroutines.launch

class MahasiswaViewModel (private val repositoryMhs: RepositoryMhs) : ViewModel(){

    var uiState by mutableStateOf(MhsUiState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    //Validasi input nama pengguna
    private fun validateFields(): Boolean {
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "Nim tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "jenisKelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "alamat tidak boleh kosong",
            kelas = if (event.kelas.isNotEmpty()) null else "kelas tidak boleh kosong",
            angkatan = if (event.angkatan.isNotEmpty()) null else "angkatan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    //menyimpan data ke repository
    fun saveData() {
        val currentEvent = uiState.mahasiswaEvent
        if(validateFields()) {
            viewModelScope.launch {
                try{
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(), //reset input form
                        isEntryValid = FormErrorState() // reset error state
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal disimpamn"
                    )
                }
            }
        }else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak Valid, Periksa kembali data Anda."
            )
        }
    }

    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MhsUiState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)

data class FormErrorState(
    val nim: String? =null,
    val nama: String? =null,
    val jenisKelamin: String? =null,
    val alamat: String? =null,
    val kelas: String? =null,
    val angkatan: String? =null,
){
    fun isValid(): Boolean {
        return nim == null && nama == null && jenisKelamin == null &&
                alamat == null && kelas == null && angkatan == null
    }
}

//data class variabel yang menyimpan data input form
data class MahasiswaEvent(
    val nim: String = "",
    val nama: String = "",
    val jenisKelamin: String = "",
    val alamat: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

//Menyimpan input form kedalam entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)