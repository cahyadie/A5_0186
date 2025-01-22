package com.example.ujianakhir.ui.viewmodel

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.ujianakhir.ui.navigasi.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllApp(modifier: Modifier){
    val scrollBehaivor = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaivor.nestedScrollConnection),
    ){
        Surface(
            modifier = Modifier.fillMaxSize().padding(it)
        ){
            PengelolaHalaman()
        }
    }
}
