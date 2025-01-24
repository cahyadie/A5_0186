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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.UpdateDktrUiEvent
import com.example.ujianakhir.ui.viewmodel.dokter.UpdateDokterViewModel
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDokterScreen(
    navigateBack: () -> Unit,
    iddokter: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateDokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val dktruiState = viewModel.DktruiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(iddokter) {
        viewModel.getDokterById(iddokter)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Dokter",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiEvent = dktruiState.updateUiEvent,
            onDokterValueChange = viewModel::UpdateDktrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateDokter()
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
fun UpdateBody(
    updateUiEvent: UpdateDktrUiEvent,
    onDokterValueChange: (UpdateDktrUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiEvent,
            onValueChange = onDokterValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Update")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    updateUiEvent: UpdateDktrUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateDktrUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.namadokter,
            onValueChange = { onValueChange(updateUiEvent.copy(namadokter = it)) },
            label = { Text("Nama Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.spesialisasi,
            onValueChange = { onValueChange(updateUiEvent.copy(spesialisasi = it)) },
            label = { Text("Spesialisasi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.kontak,
            onValueChange = { onValueChange(updateUiEvent.copy(kontak = it)) },
            label = { Text("Kontak ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}