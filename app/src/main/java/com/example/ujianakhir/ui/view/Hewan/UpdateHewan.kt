package com.example.ujianakhir.ui.view.Hewan

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
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateHewanScreen(
    navigateBack: () -> Unit,
    idhewan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdateHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val hwnuiState = viewModel.hwnuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idhewan) {
        viewModel.getHewanById(idhewan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Hewan",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiEvent = hwnuiState.updateUiEvent,
            onHewanValueChange = viewModel::UpdateHwnState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateHewan()
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
    updateUiEvent: UpdateHwnUiEvent,
    onHewanValueChange: (UpdateHwnUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiEvent,
            onValueChange = onHewanValueChange,
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
    updateUiEvent: UpdateHwnUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdateHwnUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.namahewan,
            onValueChange = { onValueChange(updateUiEvent.copy(namahewan = it)) },
            label = { Text("Nama Hewan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.jenishewanid,
            onValueChange = { onValueChange(updateUiEvent.copy(jenishewanid = it)) },
            label = { Text("Jenis Hewan ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.kontakpemilik,
            onValueChange = { onValueChange(updateUiEvent.copy(kontakpemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.tanggallahir,
            onValueChange = { onValueChange(updateUiEvent.copy(tanggallahir = it)) },
            label = { Text("Tanggal Lahir") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.pemilik,
            onValueChange = { onValueChange(updateUiEvent.copy(pemilik = it)) },
            label = { Text("Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.catatankesehatan,
            onValueChange = { onValueChange(updateUiEvent.copy(catatankesehatan = it)) },
            label = { Text("Catatan Kesehatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}