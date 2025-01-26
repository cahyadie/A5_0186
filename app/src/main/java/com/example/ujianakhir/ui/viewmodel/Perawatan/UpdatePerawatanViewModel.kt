package com.example.ujianakhir.ui.viewmodel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.repository.PerawatanRepository
import kotlinx.coroutines.launch

class UpdatePerawatanViewModel(
    private val prwt: PerawatanRepository
) : ViewModel() {

    var prwtuiState by mutableStateOf(UpdateUiState())
        private set

    fun UpdateprwtState(updateUiEvent: UpdatePrwtUiEvent) {
        prwtuiState = prwtuiState.copy(updateUiEvent = updateUiEvent)
    }

    fun getPerawatanById(idperawatan: String) {
        viewModelScope.launch {
            try {
                val idperawatan = prwt.getPerawatanById(idperawatan)
                prwtuiState = UpdateUiState(updateUiEvent = idperawatan.toUpdatePrwtUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                prwtuiState = UpdateUiState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updatePerawatan() {
        viewModelScope.launch {
            try {
                val perawatan = prwtuiState.updateUiEvent.toPrwt()
                prwt.updatePerawatan(perawatan.idperawatan, perawatan)
                prwtuiState = prwtuiState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                prwtuiState = prwtuiState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateUiState(
    val updateUiEvent: UpdatePrwtUiEvent = UpdatePrwtUiEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdatePrwtUiEvent(
    val idperawatan: String = "",
    val idhewan: String = "",
    val iddokter: String = "",
    val tanggalperawatan: String = "",
    val detailperawatan: String = ""
)

fun UpdatePrwtUiEvent.toPrwt(): Perawatan = Perawatan(
    idperawatan = idperawatan,
    iddokter = iddokter,
    idhewan = idhewan,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)

fun Perawatan.toUpdatePrwtUiEvent(): UpdatePrwtUiEvent = UpdatePrwtUiEvent(
    idperawatan = idperawatan,
    iddokter = iddokter,
    idhewan = idhewan,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)