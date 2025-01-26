package com.example.ujianakhir.ui.view.Perawatan

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
import com.example.ujianakhir.ui.viewmodel.Perawatan.UpdatePerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.UpdatePrwtUiEvent
import com.example.ujianakhir.ui.viewmodel.jenishewan.UpdateJnsUiEvent
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePerawatanScreen(
    navigateBack: () -> Unit,
    idperawatan: String,
    modifier: Modifier = Modifier,
    viewModel: UpdatePerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val prwtuiState = viewModel.prwtuiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(idperawatan) {
        viewModel.getPerawatanById(idperawatan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Update Perawatan",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        UpdateBody(
            updateUiEvent = prwtuiState.updateUiEvent,
            onPerawatanValueChange = viewModel::UpdateprwtState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePerawatan()
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
    updateUiEvent: UpdatePrwtUiEvent,
    onPerawatanValueChange: (UpdatePrwtUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            updateUiEvent = updateUiEvent,
            onValueChange = onPerawatanValueChange,
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
    updateUiEvent: UpdatePrwtUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (UpdatePrwtUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = updateUiEvent.idhewan,
            onValueChange = { onValueChange(updateUiEvent.copy(idhewan = it)) },
            label = { Text("Id Hewan  ") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.iddokter,
            onValueChange = { onValueChange(updateUiEvent.copy(iddokter = it)) },
            label = { Text("Id Dokter") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.idperawatan,
            onValueChange = { onValueChange(updateUiEvent.copy(idperawatan = it)) },
            label = { Text("Id Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.tanggalperawatan,
            onValueChange = { onValueChange(updateUiEvent.copy(tanggalperawatan = it)) },
            label = { Text("tanggal perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = updateUiEvent.detailperawatan,
            onValueChange = { onValueChange(updateUiEvent.copy(detailperawatan = it)) },
            label = { Text("Detail Perawatan") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}