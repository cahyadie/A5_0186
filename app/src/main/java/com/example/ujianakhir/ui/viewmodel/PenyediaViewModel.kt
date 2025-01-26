package com.example.ujianakhir.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ujianakhir.App
import com.example.ujianakhir.ui.viewmodel.Perawatan.DetailPerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.HomePerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.InsertPerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.Perawatan.UpdatePerawatanViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.DetailDokterViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.HomeDokterViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.InsertDokterViewModel
import com.example.ujianakhir.ui.viewmodel.dokter.UpdateDokterViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.ujianakhir.ui.viewmodel.hewan.UpdateHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.DetailJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.HomeJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.InsertJenisHewanViewModel
import com.example.ujianakhir.ui.viewmodel.jenishewan.UpdateJenisHewanViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(App().container.hewanRepository) }
        initializer { InsertHewanViewModel(
            App().container.hewanRepository,
            App().container.jenisHewanRepository
            ) }
        initializer { UpdateHewanViewModel(
            App().container.hewanRepository,
            App().container.jenisHewanRepository
            ) }
        initializer { DetailHewanViewModel(createSavedStateHandle(),App().container.hewanRepository) }

        initializer { HomeDokterViewModel(App().container.dokterRepository) }
        initializer { InsertDokterViewModel(App().container.dokterRepository) }
        initializer { UpdateDokterViewModel(App().container.dokterRepository) }
        initializer { DetailDokterViewModel(createSavedStateHandle(),App().container.dokterRepository) }

        initializer { HomeJenisHewanViewModel(App().container.jenisHewanRepository) }
        initializer { InsertJenisHewanViewModel(App().container.jenisHewanRepository) }
        initializer { UpdateJenisHewanViewModel(App().container.jenisHewanRepository) }
        initializer { DetailJenisHewanViewModel(createSavedStateHandle(),App().container.jenisHewanRepository) }

        initializer { InsertPerawatanViewModel(
            App().container.perawatanRepository,
            App().container.hewanRepository,
            App().container.dokterRepository
            ) }
        initializer { HomePerawatanViewModel(App().container.perawatanRepository) }
        initializer { UpdatePerawatanViewModel(App().container.perawatanRepository) }
        initializer { DetailPerawatanViewModel(createSavedStateHandle(),App().container.perawatanRepository)}

    }
}

fun CreationExtras.App(): App =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as App)
