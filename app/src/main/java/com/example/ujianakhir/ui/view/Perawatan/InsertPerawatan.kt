package com.example.ujianakhir.ui.view.Perawatan

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.InsertPerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.InsertUiEvent
import com.example.ujianakhir.ui.viewmodel.Perawatan.InsertUiState
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

object DestinasiEntryPerawatan : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Insert Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPrwtScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: InsertPerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaivor = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaivor.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPerawatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehaivor,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewmodel.prwtuiState,
            onPerawatanValueChange = viewmodel::updtaeInsertPrwtState,
            hwnList = viewmodel.hwnListInsert,
            dktrList = viewmodel.dktrListInsert,
            onSaveClick = {
                coroutineScope.launch {
                    viewmodel.insertPrwt()
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
    onPerawatanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    hwnList: List<String> = emptyList(),
    dktrList: List<String> = emptyList()
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onPerawatanValueChange,
            hwnList = hwnList,
            dktrList = dktrList,
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
    enabled: Boolean = true,
    hwnList: List<String> = emptyList(),
    dktrList: List<String> = emptyList()
){
    var expandedhwn by remember { mutableStateOf(false) }
    var expandeddktr by remember { mutableStateOf(false) }
    var selectedhwn by remember { mutableStateOf("") }
    var selecteddktr by remember { mutableStateOf("") }


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        ExposedDropdownMenuBox(
            expanded = expandedhwn,
            onExpandedChange = { expandedhwn = !expandedhwn }
        ) {


            OutlinedTextField(
                value = selectedhwn,
                onValueChange = {},
                label = { Text("Id Hewan") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedhwn)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandedhwn,
                onDismissRequest = { expandedhwn = false }
            ) {
                hwnList.forEach { hewan ->
                    DropdownMenuItem(
                        text = { Text(hewan) },
                        onClick = {
                            selectedhwn = hewan
                            onValueChange(insertUiEvent.copy(idhewan = hewan))
                            expandedhwn = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandeddktr,
            onExpandedChange = { expandeddktr = !expandeddktr }
        ) {
            OutlinedTextField(
                value = selecteddktr,
                onValueChange = {},
                label = { Text("Id Dokter") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandeddktr)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expandeddktr,
                onDismissRequest = { expandeddktr = false }
            ) {
                dktrList.forEach { dokter ->
                    DropdownMenuItem(
                        text = { Text(dokter) },
                        onClick = {
                            selecteddktr = dokter
                            onValueChange(insertUiEvent.copy(iddokter = dokter))
                            expandeddktr = false
                        }
                    )
                }
            }
        }
        OutlinedTextField(
            value = insertUiEvent.idperawatan,
            onValueChange = {onValueChange(insertUiEvent.copy(idperawatan = it))},
            label = { Text("Id Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertUiEvent.tanggalperawatan,
            onValueChange = {onValueChange(insertUiEvent.copy(tanggalperawatan = it))},
            label = { Text("tanggalperawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.detailperawatan,
            onValueChange = {onValueChange(insertUiEvent.copy(detailperawatan = it))},
            label = { Text("detailperawatan") },
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