package com.example.ujianakhir.ui.viewmodel.jenishewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.repository.JenisHewanRepository
import kotlinx.coroutines.launch

class UpdateJenisHewanViewModel(
    private val jns: JenisHewanRepository
) : ViewModel() {

    var jnsuiState by mutableStateOf(UpdateUiState())
        private set

    fun UpdateJNsState(updateUiEvent: UpdateJnsUiEvent) {
        jnsuiState = jnsuiState.copy(updateUiEvent = updateUiEvent)
    }

    fun getJenisHewanById(jenishewanid: String) {
        viewModelScope.launch {
            try {
                val jenishewan = jns.getJenisHewanById(jenishewanid)
                jnsuiState = UpdateUiState(updateUiEvent = jenishewan.toUpdateJnsUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                jnsuiState = UpdateUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateJenisHewan() {
        viewModelScope.launch {
            try {
                val jenishewan = jnsuiState.updateUiEvent.toJns()
                jns.updateJenisHewan(jenishewan.jenishewanid, jenishewan)
                jnsuiState = jnsuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                jnsuiState = jnsuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateUiState(
    val updateUiEvent: UpdateJnsUiEvent = UpdateJnsUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateJnsUiEvent(
    val jenishewanid: String = "",
    val namajenishewan: String = "",
    val deskripsi: String = ""
)

fun UpdateJnsUiEvent.toJns(): JenisHewan = JenisHewan(
    jenishewanid = jenishewanid,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)

fun JenisHewan.toUpdateJnsUiEvent(): UpdateJnsUiEvent = UpdateJnsUiEvent(
    jenishewanid = jenishewanid,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)