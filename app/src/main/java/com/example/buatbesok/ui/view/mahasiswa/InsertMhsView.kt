package com.example.apllicationdata.ui.view.mahasiswa


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.buatbesok.ui.costumwidget.CustomTopAppBar
import com.example.buatbesok.ui.navigation.AlamatNavigasi
import com.example.buatbesok.ui.viewmodel.FormErrorState
import com.example.buatbesok.ui.viewmodel.MahasiswaEvent
import com.example.buatbesok.ui.viewmodel.MahasiswaViewModel
import com.example.buatbesok.ui.viewmodel.MhsUiState
import com.example.buatbesok.ui.viewmodel.PenyediaViewModel

import kotlinx.coroutines.launch


object DestinasiInsert : AlamatNavigasi {
    override val route: String = "insert_mhs"
}

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel (factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState // Ambil UI state dari ViewModel
    val snackbarHostState = remember{ SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    //Observasi perubahan snackbarMessage

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ){

            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mahasiswa"
            )

            //Isi Body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData() //Simpan data
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUiState,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()

        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMahasiswa(
    mahasiswaEvent:MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {

    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan Nama") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim, onValueChange = {
                onValueChange(mahasiswaEvent.copy(nim = it))
            },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Text(text = errorState.nim ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            jenisKelamin.forEach { jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))
                        },

                        )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = errorState.jenisKelamin ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan Alamat") },
        )
        Text(text = errorState.alamat ?: "", color = Color.Red)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Kelas")
        Row {
            kelas.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kelas,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kelas))
                        },

                        )
                    Text(
                        text = kelas,
                    )
                }
            }
        }
        Text(
            text = errorState.kelas ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(angkatan = it))
            },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan Angkatan") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.angkatan ?: "", color = Color.Red)

    }
}