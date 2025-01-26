package com.example.ujianakhir.ui.view.Perawatan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.DetailPerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.DetailprwtUiState
import com.example.ujianakhir.ui.viewmodel.jenishewan.DetailJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.DetailjnsUiState
import com.example.ujianakhir.ui.widget.CostumeTopAppBar


object DestinasiDetailPerawatan : DestinasiNavigasi {
    override val route = "detail_Perawatan"
    override val titleRes = "Detail Perawatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPerawatanScreen(
    navigateBack: () -> Unit,
    idperawatan: String,
    modifier: Modifier = Modifier,
    viewModel: DetailPerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val prwtuiState by viewModel.detailprwtUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getPerawatan(idperawatan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPerawatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp), // Mengatur jarak dari tepi layar
                contentAlignment = Alignment.BottomEnd
            ){
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp), // Jarak horizontal antar tombol
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    FloatingActionButton(
                        onClick = {
                            navController.navigate("updatePerawatan/$idperawatan")
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Perawatan")
                    }
                    FloatingActionButton(
                        onClick = {
                            // Action untuk delete
                            viewModel.deletePerawatan(idperawatan)
                            navController.navigateUp() // Kembali setelah delete
                        },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color.Red
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Perawatan")
                    }
                }
            }
        }
    ) { innerPadding ->
        DetailBodyPerawatan(
            detailprwtUiState = prwtuiState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyPerawatan(
    detailprwtUiState: DetailprwtUiState,
    modifier: Modifier = Modifier
) {
    when (detailprwtUiState) {
        is DetailprwtUiState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailprwtUiState.Error -> {
            Text(
                text = detailprwtUiState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailprwtUiState.Success -> {
            val perawatan = detailprwtUiState.perawatan
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailPrwt(judul = "Jenis Hewan ID", isinya = perawatan.idperawatan)
                ComponentDetailPrwt(judul = "Nama Jenis Hewan", isinya = perawatan.idhewan)
                ComponentDetailPrwt(judul = "Deskripsi", isinya = perawatan.iddokter)
                ComponentDetailPrwt(judul = "Deskripsi", isinya = perawatan.tanggalperawatan)
                ComponentDetailPrwt(judul = "Deskripsi", isinya = perawatan.detailperawatan)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailPrwt(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )

        Text(
            text = isinya,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
    }
}