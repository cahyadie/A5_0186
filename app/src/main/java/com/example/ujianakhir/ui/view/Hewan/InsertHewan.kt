package com.example.ujianakhir.ui.view.Hewan

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
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.InsertUiEvent
import com.example.ujianakhir.ui.viewmodel.hewan.InsertUiState
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch


object DestinasiEntryHewan : DestinasiNavigasi {
    override val route = "item_entryhewan"
    override val titleRes = "Insert Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryHwnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: InsertHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaivor = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaivor.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryHewan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehaivor,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewmodel.uiState,
            onHewanValueChange = viewmodel::updtaeInsertHwnState,
            jnsList = viewmodel.jnsListInsert,
            onSaveClick = {
                coroutineScope.launch {
                    viewmodel.insertHwn()
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
    onHewanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
    jnsList: List<String> = emptyList()

){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onHewanValueChange,
            jnsList = jnsList,
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
    jnsList: List<String> = emptyList()
){
    var expanded by remember { mutableStateOf(false) }
    var selectedJenis by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertUiEvent.idhewan,
            onValueChange = {onValueChange(insertUiEvent.copy(idhewan = it))},
            label = { Text("idhewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.namahewan,
            onValueChange = {onValueChange(insertUiEvent.copy(namahewan = it))},
            label = { Text("namahewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedJenis,
                onValueChange = {},
                label = { Text("Jenis Hewan") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                jnsList.forEach { jenis ->
                    DropdownMenuItem(
                        text = { Text(jenis) },
                        onClick = {
                            selectedJenis = jenis
                            onValueChange(insertUiEvent.copy(jenishewanid = jenis))
                            expanded = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = insertUiEvent.kontakpemilik,
            onValueChange = {onValueChange(insertUiEvent.copy(kontakpemilik = it))},
            label = { Text("kontakpemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.tanggallahir,
            onValueChange = {onValueChange(insertUiEvent.copy(tanggallahir = it))},
            label = { Text("tanggallahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.pemilik,
            onValueChange = {onValueChange(insertUiEvent.copy(pemilik = it))},
            label = { Text("pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.catatankesehatan,
            onValueChange = {onValueChange(insertUiEvent.copy(catatankesehatan = it))},
            label = { Text("catatankesehatan") },
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