package com.example.ujianakhir.ui.view.Perawatan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ujianakhir.R
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.HomePerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.HomeUiState
import com.example.ujianakhir.ui.widget.CostumeTopAppBar

object DestinasiHomePerawatan : DestinasiNavigasi {
    override val route = "home_perawatan"
    override val titleRes = "Riwayat Kesehatan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenPerawatan(
    navigateBack: () -> Unit,
    navigateBackPerawat: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePerawatanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePerawatan.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBackPerawat,
                onRefresh = {
                    viewModel.getPerawatan()
                }
            )
        },

    ){innerPadding ->
        HomeStatusPerawatan(
            homeUiState = viewModel.prwtUIState,
            retryAction = {viewModel.getPerawatan()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deletePerawatan(it.idperawatan)
                viewModel.getPerawatan()
            }
        )
    }
}

@Composable
fun HomeStatusPerawatan(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Perawatan) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.perawatan.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data")
                }
            } else {
                PrwtLayout(
                    perawatan = homeUiState.perawatan, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idperawatan)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    }
                )
            }

        is HomeUiState.Error -> OnErorr(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.ic_launcher_background),
        contentDescription = stringResource(R.string.app_name)
    )
}

@Composable
fun OnErorr(retryAction: () -> Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = ""
        )
        Text(text = stringResource(R.string.app_name), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction){
            Text(stringResource(R.string.app_name))
        }
    }
}

@Composable
fun PrwtLayout(
    perawatan: List<Perawatan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Perawatan) -> Unit,
    onDeleteClick: (Perawatan) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(perawatan) { perawatan ->
            PerawatanCard(
                perawatan = perawatan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(perawatan)},
                onDeleteClick = {
                    onDeleteClick(perawatan)
                }
            )
        }
    }
}

@Composable
fun PerawatanCard(
    perawatan: Perawatan,
    modifier: Modifier = Modifier,
    onDeleteClick: (Perawatan) -> Unit ={}
){
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = perawatan.idperawatan,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(perawatan)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = perawatan.idhewan,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = perawatan.iddokter,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = perawatan.tanggalperawatan,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = perawatan.detailperawatan,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}