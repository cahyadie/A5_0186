package com.example.ujianakhir.ui.menuScreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.view.Hewan.HewanCard
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.HomeUiState

object DestinasiMenu : DestinasiNavigasi {
    override val route = "Menu"
    override val titleRes = "Menu Utama"
}

@Composable
fun MenuScreen(
    onNavigateToHewan: () -> Unit,
    onNavigateToDokter: () -> Unit,
    onNavigateToJenisHewan: () -> Unit,
    onNavigateToPerawatan: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeHewanViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onDetailClick: (String) -> Unit = {}
) {
    val homeUiState = viewModel.hwnUIState
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0B4D4D), Color(0xFF125E5E), Color(0xFF188383))
                )
            )
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo dan teks di bagian atas
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Rumah Sakit Hewan Azril",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(20.dp)
                        .shadow(2.dp, shape = RoundedCornerShape(4.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButtonWithImage(
                    iconResId = R.drawable.iconhewan, // Ganti dengan ikon "Hewan"
                    color = Color(0xFF126E6E),
                    text = "Hewan",
                    onClick = onNavigateToHewan
                )
                IconButtonWithImage(
                    iconResId = R.drawable.doktericon, // Ganti dengan ikon "Dokter"
                    color = Color(0xFF1A8C8C),
                    text = "Dokter",
                    onClick = onNavigateToDokter
                )
                IconButtonWithImage(
                    iconResId = R.drawable.jenishewanicon, // Ganti dengan ikon "Jenis Hewan"
                    color = Color(0xFF20A4A4),
                    text = "Jenis Hewan",
                    onClick = onNavigateToJenisHewan
                )
                IconButtonWithImage(
                    iconResId = R.drawable.calendar, // Ganti dengan ikon "Riwayat Kesehatan"
                    color = Color(0xFF20A4A4),
                    text = "RiwayatKesehatan",
                    onClick = onNavigateToPerawatan
                )
            }
            when (homeUiState) {
                is HomeUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                is HomeUiState.Success -> {
                    val hewanList = homeUiState.hewan
                    if (hewanList.isEmpty()) {
                        Text("Tidak ada data hewan.", color = Color.White)
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(hewanList) { hewan ->
                                HewanCard(
                                    hewan = hewan,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp),
                                    onDeleteClick = {
                                        viewModel.deleteHewan(hewan.idhewan)
                                    }
                                )
                            }
                        }
                    }
                }
                is HomeUiState.Error -> {
                    Text(
                        text = "Gagal memuat data.",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun IconButtonWithImage(
    @DrawableRes iconResId: Int,
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = color,
                contentColor = Color.White
            ),
            shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}
