package com.example.ujianakhir.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.repository.HewanRepository
import com.example.ujianakhir.repository.JenisHewanRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InsertHewanViewModel(
    private val hwn: HewanRepository,
    private val jns: JenisHewanRepository

) : ViewModel(){
    var uiState by mutableStateOf(InsertUiState())
        private set

    val jnsListState: StateFlow<List<JenisHewan>> = flow {
        emit(jns.getJenisHewan())
    }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    var jnsListInsert by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchjnsList()
    }

    fun fetchjnsList(){
        viewModelScope.launch {
            try {
                val jnsList = jns.getJenisHewan()
                jnsListInsert = jnsList
                    .map {it.jenishewanid}
            } catch (e: Exception) {
                jnsListInsert = emptyList()
            }
        }
    }

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
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val snackbarMessage : String? = null
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