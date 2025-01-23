package com.example.ujianakhir.ui.viewmodel.jenishewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.repository.JenisHewanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState{
    data class Success(val jenisHewan: List<JenisHewan>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeJenisHewanViewModel(private val jnshwn: JenisHewanRepository): ViewModel(){
    var jnshwnUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getJenisHewan()
    }

    fun getJenisHewan(){
        viewModelScope.launch {
            jnshwnUIState = HomeUiState.Loading
            jnshwnUIState = try{
                HomeUiState.Success(jnshwn.getJenisHewan())
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteJenisHewan(jenishewanid: String){
        viewModelScope.launch {
            try {
                jnshwn.deleteJenisHewan(jenishewanid)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    }
}