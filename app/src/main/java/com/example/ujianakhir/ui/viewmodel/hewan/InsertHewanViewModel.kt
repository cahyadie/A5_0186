package com.example.ujianakhir.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.repository.HewanRepository
import kotlinx.coroutines.launch

class InsertHewanViewModel(private val hwn: HewanRepository) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updtaeInsertHwnState(insertUiEvent: InsertUiEvent){
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertHwn(){
        viewModelScope.launch {
            try{
                hwn.insertHewan(uiState.insertUiEvent.toHwn())
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
    val idhewan: String = "",
    val namahewan: String = "",
    val jenishewanid: String = "",
    val kontakpemilik: String = "",
    val tanggallahir: String = "",
    val pemilik: String = "",
    val catatankesehatan: String = ""
)

fun InsertUiEvent.toHwn(): Hewan = Hewan(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    pemilik = pemilik,
    catatankesehatan = catatankesehatan
)

fun Hewan.toUiStateHwn(): InsertUiState = InsertUiState(
    insertUiEvent = InsertUiEvent()
)

fun Hewan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    pemilik = pemilik,
    catatankesehatan = catatankesehatan
)