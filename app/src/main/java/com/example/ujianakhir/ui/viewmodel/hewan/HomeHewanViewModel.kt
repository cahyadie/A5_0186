package com.example.ujianakhir.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.repository.HewanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class HomeUiState{
    data class Success(val hewan: List<Hewan>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeHewanViewModel(private val hwn: HewanRepository): ViewModel(){
    var hwnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getHewan()
    }

    fun getHewan(){
        viewModelScope.launch {
            hwnUIState = HomeUiState.Loading
            hwnUIState = try{
                HomeUiState.Success(hwn.getHewan())
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteHewan(idhewan: String){
        viewModelScope.launch {
            try {
                hwn.deleteHewan(idhewan)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    }
}