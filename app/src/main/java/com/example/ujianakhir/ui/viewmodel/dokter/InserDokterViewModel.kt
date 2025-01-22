package com.example.ujianakhir.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Dokter
import com.example.ujianakhir.repository.DokterRepository
import kotlinx.coroutines.launch


class InsertDokterViewModel(private val dktr: DokterRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updtaeInsertDktrState(insertUiEvent: InsertUiEvent){
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertDktr(){
        viewModelScope.launch {
            try{
                dktr.insertDokter(uiState.insertUiEvent.toDktr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val iddokter: String = "",
    val namadokter: String = "",
    val spesialisasi: String = "",
    val kontak: String = ""
)

fun InsertUiEvent.toDktr(): Dokter = Dokter(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)

fun Dokter.toUiStateDktr(): InsertUiState = InsertUiState(
    insertUiEvent = InsertUiEvent()
)

fun Dokter.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)