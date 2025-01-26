package com.example.ujianakhir.ui.viewmodel.Perawatan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.repository.PerawatanRepository
import com.example.ujianakhir.ui.viewmodel.hewan.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailprwtUiState {
    data class Success(val perawatan: Perawatan) : DetailprwtUiState()
    data class Error(val message: String) : DetailprwtUiState()
    object Loading : DetailprwtUiState()
}

class DetailPerawatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val prwt: PerawatanRepository
) : ViewModel() {
    private val diperawatan: String = checkNotNull(savedStateHandle["idperawatan"])

    private val _detailprwtUiState = MutableStateFlow<DetailprwtUiState>(DetailprwtUiState.Loading)
    val detailprwtUiState: StateFlow<DetailprwtUiState> = _detailprwtUiState.asStateFlow()

    fun getPerawatan(idperawatan: String) {
        viewModelScope.launch {
            _detailprwtUiState.value = DetailprwtUiState.Loading
            _detailprwtUiState.value = try {
                val perawatan = prwt.getPerawatanById(idperawatan)
                DetailprwtUiState.Success(perawatan)
            } catch (e: IOException) {
                DetailprwtUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailprwtUiState.Error("Terjadi kesalahan server")
            }
        }
    }
    fun deletePerawatan(idperawatan: String){
        viewModelScope.launch {
            try {
                prwt.deletePerawatan(idperawatan)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}