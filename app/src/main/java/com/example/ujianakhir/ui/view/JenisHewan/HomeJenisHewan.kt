package com.example.ujianakhir.ui.view.JenisHewan

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.ui.navigasi.DestinasiNavigasi
import com.example.ujianakhir.ui.viewmodel.PenyediaViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.HomeJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.HomeUiState
import com.example.ujianakhir.ui.widget.CostumeTopAppBar

object DestinasiHomeJenisHewan : DestinasiNavigasi {
    override val route = "home_jenishewan"
    override val titleRes = "Home JenisHewan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenJenisHewan(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeJenisHewanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeJenisHewan.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getJenisHewan()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add JenisHewan")
            }
        },
    ){innerPadding ->
        HomeStatusJenisHewan(
            homeUiState = viewModel.mhsUIState,
            retryAction = {viewModel.getJenisHewan()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick, onDeleteClick = {
                viewModel.deleteJenisHewan(it.jenishewanid)
                viewModel.getJenisHewan()
            }
        )
    }
}

@Composable
fun HomeStatusJenisHewan(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisHewan) -> Unit = {},
    onDetailClick: (String) -> Unit
){
    when(homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is HomeUiState.Success ->
            if (homeUiState.jenisHewan.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada Data")
                }
            } else {
                JnsHwnLayout(
                    jenisHewan = homeUiState.jenisHewan, modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.jenishewanid)
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
fun JnsHwnLayout(
    jenisHewan: List<JenisHewan>,
    modifier: Modifier = Modifier,
    onDetailClick: (JenisHewan) -> Unit,
    onDeleteClick: (JenisHewan) -> Unit = {}
){
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(jenisHewan) { jenisHewan ->
            JenisHewanCard(
                jenisHewan = jenisHewan,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(jenisHewan)},
                onDeleteClick = {
                    onDeleteClick(jenisHewan)
                }
            )
        }
    }
}

@Composable
fun JenisHewanCard(
    jenisHewan: JenisHewan,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisHewan) -> Unit ={}
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
                    text = jenisHewan.namajenishewan,
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {onDeleteClick(jenisHewan)}) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = jenisHewan.jenishewanid,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Text(
                text = jenisHewan.deskripsi,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}