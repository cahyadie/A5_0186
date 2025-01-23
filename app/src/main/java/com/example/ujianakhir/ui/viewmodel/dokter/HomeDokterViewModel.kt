package com.example.ujianakhir.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Dokter
import com.example.ujianakhir.repository.DokterRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class HomeUiState{
    data class Success(val dokter: List<Dokter>): HomeUiState()
    object Error: HomeUiState()
    object Loading: HomeUiState()
}

class HomeDokterViewModel(private val dktr: DokterRepository): ViewModel(){
    var dktrUIState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        getdokter()
    }

    fun getdokter(){
        viewModelScope.launch {
            dktrUIState = HomeUiState.Loading
            dktrUIState = try{
                HomeUiState.Success(dktr.getDokter())
            }catch (e: IOException){
                HomeUiState.Error
            }catch (e: HttpException){
                HomeUiState.Error
            }
        }
    }

    fun deleteDokter(iddokter: String){
        viewModelScope.launch {
            try {
                dktr.deleteDokter(iddokter)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }

    }
}