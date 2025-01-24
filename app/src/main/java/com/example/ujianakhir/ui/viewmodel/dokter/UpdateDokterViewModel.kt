package com.example.ujianakhir.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Dokter
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.repository.DokterRepository
import com.example.ujianakhir.repository.HewanRepository
import kotlinx.coroutines.launch

class UpdateDokterViewModel(
    private val dktr: DokterRepository
) : ViewModel() {

    var DktruiState by mutableStateOf(UpdateUiState())
        private set

    fun UpdateDktrState(updateUiEvent: UpdateDktrUiEvent) {
        DktruiState = DktruiState.copy(updateUiEvent = updateUiEvent)
    }

    fun getDokterById(iddokter: String) {
        viewModelScope.launch {
            try {
                val hewan = dktr.getDokterById(iddokter)
                DktruiState = UpdateUiState(updateUiEvent = hewan.toUpdateDktrUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                DktruiState = UpdateUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateDokter() {
        viewModelScope.launch {
            try {
                val dokter = DktruiState.updateUiEvent.toDktr()
                dktr.updateDokter(dokter.iddokter, dokter)
                DktruiState = DktruiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                DktruiState = DktruiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateUiState(
    val updateUiEvent: UpdateDktrUiEvent = UpdateDktrUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateDktrUiEvent(
    val iddokter: String = "",
    val namadokter: String = "",
    val spesialisasi: String = "",
    val kontak: String = ""
)

fun UpdateDktrUiEvent.toDktr(): Dokter = Dokter(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak,
)

fun Dokter.toUpdateDktrUiEvent(): UpdateDktrUiEvent = UpdateDktrUiEvent(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak,
)