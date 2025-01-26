package com.example.ujianakhir.ui.viewmodel.jenishewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.repository.JenisHewanRepository
import com.example.ujianakhir.ui.viewmodel.hewan.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailjnsUiState {
    data class Success(val jenisHewan: JenisHewan) : DetailjnsUiState()
    data class Error(val message: String) : DetailjnsUiState()
    object Loading : DetailjnsUiState()
}

class DetailJenisHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisHewanRepository
) : ViewModel() {
    private val jenishewanid: String = checkNotNull(savedStateHandle["jenishewanid"])

    private val _detailjnsUiState = MutableStateFlow<DetailjnsUiState>(DetailjnsUiState.Loading)
    val detailjnsUiState: StateFlow<DetailjnsUiState> = _detailjnsUiState.asStateFlow()

    fun getJenisHewan(jenishewanid: String) {
        viewModelScope.launch {
            _detailjnsUiState.value = DetailjnsUiState.Loading
            _detailjnsUiState.value = try {
                val jenisHewan = jns.getJenisHewanById(jenishewanid)
                DetailjnsUiState.Success(jenisHewan)
            } catch (e: IOException) {
                DetailjnsUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailjnsUiState.Error("Terjadi kesalahan server")
            }
        }
    }
    fun deleteJenisHewan(jenishewanid: String){
        viewModelScope.launch {
            try {
                jns.deleteJenisHewan(jenishewanid)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }
}