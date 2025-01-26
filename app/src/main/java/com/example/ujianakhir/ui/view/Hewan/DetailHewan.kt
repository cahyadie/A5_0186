package com.example.ujianakhir.ui.view.Hewan

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
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
import com.example.ujianakhir.ui.widget.CostumeTopAppBar
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.DetailhwnUiState

object DestinasiDetailHewan : DestinasiNavigasi {
    override val route = "detail_Hewan"
    override val titleRes = "Detail Hewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailHewanScreen(
    navigateBack: () -> Unit,
    idhewan: String,
    navigateToEntryPrwt: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailHewanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    navController: NavHostController
) {
    val uiState by viewModel.detailhwnUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(Unit) {
        viewModel.getHewan(idhewan)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailHewan.titleRes,
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
                            navController.navigate("updateHewan/$idhewan")
                        },
                        shape = MaterialTheme.shapes.medium,
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Update Hewan")
                    }
                    FloatingActionButton(
                        onClick = {
                            // Action untuk delete
                            viewModel.deleteHewan(idhewan)
                            navController.navigateUp() // Kembali setelah delete
                        },
                        shape = MaterialTheme.shapes.medium,
                        containerColor = Color.Red
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Hewan")
                    }
                    Button(
                        onClick = navigateToEntryPrwt,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "Add Perawatan")
                    }
                }
            }
        }
    ) { innerPadding ->
        DetailBodyHewan(
            detailhwnUiState = uiState,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun DetailBodyHewan(
    detailhwnUiState: DetailhwnUiState,
    modifier: Modifier = Modifier
) {
    when (detailhwnUiState) {
        is DetailhwnUiState.Loading -> {
            CircularProgressIndicator(modifier = modifier.fillMaxSize())
        }
        is DetailhwnUiState.Error -> {
            Text(
                text = detailhwnUiState.message,
                color = Color.Red,
                modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        }
        is DetailhwnUiState.Success -> {
            val hewan = detailhwnUiState.hewan
            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.padding(12.dp)
            ) {
                ComponentDetailHwn(judul = "idhewan", isinya = hewan.idhewan)
                ComponentDetailHwn(judul = "Nama Hewan", isinya = hewan.namahewan)
                ComponentDetailHwn(judul = "jenishewanid", isinya = hewan.jenishewanid)
                ComponentDetailHwn(judul = "kontak pemilk", isinya = hewan.kontakpemilik)
                ComponentDetailHwn(judul = "tanggal lahir", isinya = hewan.tanggallahir)
                ComponentDetailHwn(judul = "pemilik", isinya = hewan.pemilik)
                ComponentDetailHwn(judul = "catatankesehatan", isinya = hewan.catatankesehatan)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ComponentDetailHwn(
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