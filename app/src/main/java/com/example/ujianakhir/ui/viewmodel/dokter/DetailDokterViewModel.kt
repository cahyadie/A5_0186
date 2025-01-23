package com.example.ujianakhir.ui.viewmodel.dokter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Dokter
import com.example.ujianakhir.repository.DokterRepository
import com.example.ujianakhir.ui.viewmodel.hewan.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetaildktrUiState {
    data class Success(val dokter: Dokter) : DetaildktrUiState()
    data class Error(val message: String) : DetaildktrUiState()
    object Loading : DetaildktrUiState()
}

class DetailDokterViewModel(
    savedStateHandle: SavedStateHandle,
    private val dktr: DokterRepository
) : ViewModel() {
    private val iddokter: String = checkNotNull(savedStateHandle["iddokter"])

    private val _detaildktrUiState = MutableStateFlow<DetaildktrUiState>(DetaildktrUiState.Loading)
    val detaildktrUiState: StateFlow<DetaildktrUiState> = _detaildktrUiState.asStateFlow()

    fun getDokter(iddokter: String) {
        viewModelScope.launch {
            _detaildktrUiState.value = DetaildktrUiState.Loading
            _detaildktrUiState.value = try {
                val dokter = dktr.getDokterById(iddokter)
                DetaildktrUiState.Success(dokter)
            } catch (e: IOException) {
                DetaildktrUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetaildktrUiState.Error("Terjadi kesalahan server")
            }
        }
    }
    fun deleteDokter(iddokter: String){
        viewModelScope.launch {
            try {
                dktr.deleteDokter(iddokter)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}