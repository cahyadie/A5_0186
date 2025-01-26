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

class UpdateHewanViewModel(
    private val hwn: HewanRepository,
    private val jns: JenisHewanRepository
) : ViewModel() {

    var hwnuiState by mutableStateOf(UpdateUiState())
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
        fetchjnsUdpate()
    }

    fun fetchjnsUdpate(){
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

    fun UpdateHwnState(updateUiEvent: UpdateHwnUiEvent) {
        hwnuiState = hwnuiState.copy(updateUiEvent = updateUiEvent)
    }

    fun getHewanById(idhewan: String) {
        viewModelScope.launch {
            try {
                val hewan = hwn.getHewanById(idhewan)
                hwnuiState = UpdateUiState(updateUiEvent = hewan.toUpdateHwnUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                hwnuiState = UpdateUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateHewan() {
        viewModelScope.launch {
            try {
                val hewan = hwnuiState.updateUiEvent.toHwn()
                hwn.updateHewan(hewan.idhewan, hewan)
                hwnuiState = hwnuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                hwnuiState = hwnuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateUiState(
    val updateUiEvent: UpdateHwnUiEvent = UpdateHwnUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateHwnUiEvent(
    val idhewan: String = "",
    val namahewan: String = "",
    val jenishewanid: String = "",
    val kontakpemilik: String = "",
    val tanggallahir: String = "",
    val pemilik: String = "",
    val catatankesehatan: String = ""
)

fun UpdateHwnUiEvent.toHwn(): Hewan = Hewan(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    pemilik = pemilik,
    catatankesehatan = catatankesehatan
)

fun Hewan.toUpdateHwnUiEvent(): UpdateHwnUiEvent = UpdateHwnUiEvent(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    pemilik = pemilik,
    catatankesehatan = catatankesehatan
)