package com.example.ujianakhir.ui.viewmodel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.repository.PerawatanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState{
    data class Success(val perawatan: List<Perawatan>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomePerawatanViewModel(private val prwt: PerawatanRepository): ViewModel(){
    var prwtUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getPerawatan()
    }

    fun getPerawatan(){
        viewModelScope.launch {
            prwtUIState = HomeUiState.Loading
            prwtUIState = try{
                HomeUiState.Success(prwt.getPerawatan())
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deletePerawatan(idPerawatan: String){
        viewModelScope.launch {
            try {
                prwt.deletePerawatan(idPerawatan)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    }
}