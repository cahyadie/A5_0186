package com.example.ujianakhir.ui.viewmodel.Perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ujianakhir.model.Dokter
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.repository.DokterRepository
import com.example.ujianakhir.repository.HewanRepository
import com.example.ujianakhir.repository.PerawatanRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class InsertPerawatanViewModel(
    private val prwt: PerawatanRepository,
    private val hwn: HewanRepository,
    private val dktr: DokterRepository

    ) : ViewModel(){
    var prwtuiState by mutableStateOf(InsertUiState())
        private set

    val hwnListState: StateFlow<List<Hewan>> = flow {
        emit(hwn.getHewan())
    }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    var hwnListInsert by mutableStateOf<List<String>>(emptyList())
        private set

    val dktrListState: StateFlow<List<Dokter>> = flow {
        emit(dktr.getDokter())
    }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    var dktrListInsert by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchhwnList()
    }

    init {
        fetchdktrList()
    }

    fun fetchhwnList(){
        viewModelScope.launch {
            try {
                val hwnList = hwn.getHewan()
                hwnListInsert = hwnList
                    .map {it.idhewan}
            } catch (e: Exception) {
                hwnListInsert = emptyList()
            }
        }
    }
    fun fetchdktrList(){
        viewModelScope.launch {
            try {
                val dktrList = dktr.getDokter()
                dktrListInsert = dktrList
                    .map {it.iddokter}
            } catch (e: Exception) {
                dktrListInsert = emptyList()
            }
        }
    }

    fun updtaeInsertPrwtState(insertUiEvent: InsertUiEvent){
        prwtuiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertPrwt(){
        viewModelScope.launch {
            try{
                prwt.insertPerawatan(prwtuiState.insertUiEvent.toPrwt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)

data class InsertUiEvent(
    val idperawatan: String = "",
    val idhewan: String = "",
    val iddokter: String = "",
    val namadokter: String = "",
    val tanggalperawatan: String = "",
    val detailperawatan: String = ""
)

fun InsertUiEvent.toPrwt(): Perawatan = Perawatan(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)

fun Perawatan.toUiStatePrwt(): InsertUiState = InsertUiState(
    insertUiEvent = InsertUiEvent()
)

fun Perawatan.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)