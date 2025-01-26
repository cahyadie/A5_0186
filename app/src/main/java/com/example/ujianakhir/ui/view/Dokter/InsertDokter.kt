package com.example.ujianakhir.ui.view.Dokter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.InsertDokterViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.InsertUiEvent
import com.example.ujianakhir.ui.viewmodel.dokter.InsertUiState
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

object DestinasiEntryDokter : DestinasiNavigasi {
    override val route = "item_entrydokter"
    override val titleRes = "Insert Dokter"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDktrScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: InsertDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaivor = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaivor.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryDokter.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehaivor,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewmodel.uiState,
            onDokterValueChange = viewmodel::updtaeInsertDktrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewmodel.insertDktr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onDokterValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onDokterValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit ={},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.iddokter,
            onValueChange = {onValueChange(insertUiEvent.copy(iddokter = it))},
            label = { Text("idDokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namadokter,
            onValueChange = {onValueChange(insertUiEvent.copy(namadokter = it))},
            label = { Text("namaDokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.spesialisasi,
            onValueChange = {onValueChange(insertUiEvent.copy(spesialisasi = it))},
            label = { Text("spesialisasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.kontak,
            onValueChange = {onValueChange(insertUiEvent.copy(kontak = it))},
            label = { Text("kontak") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
//        if (enabled) {
//            Text(
//                text = "Isi Semua Data",
//                modifier = Modifier.padding(start = 6.dp)
//            )
//        }
//        Divider(
//            thickness = 8.dp,
//            modifier = Modifier.padding(bottom = 10.dp)
//        )
    }
}