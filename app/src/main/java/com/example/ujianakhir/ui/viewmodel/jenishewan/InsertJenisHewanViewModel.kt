package com.example.ujianakhir.ui.viewmodel.jenishewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.repository.JenisHewanRepository
import kotlinx.coroutines.launch

class InsertJenisHewanViewModel(private val jnshwn: JenisHewanRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updtaeInsertJnsHwnState(insertUiEvent: InsertUiEvent){
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertJnsHwn(){
        viewModelScope.launch {
            try{
                jnshwn.insertJenisHewan(uiState.insertUiEvent.toJnsHwn())
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
    val jenishewanid: String = "",
    val namajenishewan: String = "",
    val deskripsi: String = "",
)

fun InsertUiEvent.toJnsHwn(): JenisHewan = JenisHewan(
    jenishewanid = jenishewanid,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)

fun JenisHewan.toUiStateJnsHwn(): InsertUiState = InsertUiState(
    insertUiEvent = InsertUiEvent()
)

fun JenisHewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    jenishewanid = jenishewanid,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi,
)