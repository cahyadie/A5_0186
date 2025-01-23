package com.example.ujianakhir.ui.viewmodel.hewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.repository.HewanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class DetailhwnUiState {
    data class Success(val hewan: Hewan) : DetailhwnUiState()
    data class Error(val message: String) : DetailhwnUiState()
    object Loading : DetailhwnUiState()
}

class DetailHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val hwn: HewanRepository
) : ViewModel() {
    private val idhewan: String = checkNotNull(savedStateHandle["idhewan"])

    private val _detailhwnUiState = MutableStateFlow<DetailhwnUiState>(DetailhwnUiState.Loading)
    val detailhwnUiState: StateFlow<DetailhwnUiState> = _detailhwnUiState.asStateFlow()

    fun getHewan(idhewan: String) {
        viewModelScope.launch {
            _detailhwnUiState.value = DetailhwnUiState.Loading
            _detailhwnUiState.value = try {
                val hewan = hwn.getHewanById(idhewan)
                DetailhwnUiState.Success(hewan)
            } catch (e: IOException) {
                DetailhwnUiState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailhwnUiState.Error("Terjadi kesalahan server")
            }
        }
    }
    fun deleteHewan(idhewan:String){
        viewModelScope.launch {
            try {
                hwn.deleteHewan(idhewan)
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e:HttpException){
                HomeUiState.Error
            }
        }
    }
}
