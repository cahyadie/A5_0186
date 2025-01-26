package com.example.ujianakhir.ui.view.JenisHewan

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
import com.example.ujianakhir.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.UpdateHwnUiEvent
import com.example.ujianakhir.ui.viewmodel.jenishewan.UpdateJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.UpdateJnsUiEvent
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateJenisHewanScreen(
    navigateBack: () -> Unit,
    jenishewanid: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val jnsuiState = viewModel.jnsuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(jenishewanid) {
        viewModel.getJenisHewanById(jenishewanid)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update JenisHewan",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiEvent = jnsuiState.updateUiEvent,
            onJenisHewanValueChange = viewModel::UpdateJNsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateJenisHewan()
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
    updateUiEvent: UpdateJnsUiEvent,
    onJenisHewanValueChange: (UpdateJnsUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiEvent,
            onValueChange = onJenisHewanValueChange,
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
    updateUiEvent: UpdateJnsUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateJnsUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.jenishewanid,
            onValueChange = { onValueChange(updateUiEvent.copy(jenishewanid = it)) },
            label = { Text("Jenis Hewan Id") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.namajenishewan,
            onValueChange = { onValueChange(updateUiEvent.copy(namajenishewan = it)) },
            label = { Text("Nama Jenis Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.deskripsi,
            onValueChange = { onValueChange(updateUiEvent.copy(deskripsi = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}