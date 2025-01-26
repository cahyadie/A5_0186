package com.example.ujianakhir.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Menu
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ujianakhir.ui.menuScreen.DestinasiMenu
import com.example.ujianakhir.ui.menuScreen.MenuScreen
import com.example.ujianakhir.ui.view.Dokter.DestinasiDetailDokter
import com.example.ujianakhir.ui.view.Dokter.DestinasiEntryDokter
import com.example.ujianakhir.ui.view.Dokter.DestinasiHomeDokter
import com.example.ujianakhir.ui.view.Dokter.DetailDokterScreen
import com.example.ujianakhir.ui.view.Dokter.EntryDktrScreen
import com.example.ujianakhir.ui.view.Dokter.HomeScreenDokter
import com.example.ujianakhir.ui.view.Dokter.UpdateDokterScreen
import com.example.ujianakhir.ui.view.Hewan.DestinasiDetailHewan
import com.example.ujianakhir.ui.view.Hewan.DestinasiEntryHewan
import com.example.ujianakhir.ui.view.Hewan.DestinasiHomeHewan
import com.example.ujianakhir.ui.view.Hewan.DetailHewanScreen
import com.example.ujianakhir.ui.view.Hewan.EntryHwnScreen
import com.example.ujianakhir.ui.view.Hewan.HomeScreenHewan
import com.example.ujianakhir.ui.view.Hewan.UpdateHewanScreen
import com.example.ujianakhir.ui.view.JenisHewan.DestinasiDetailJenisHewan
import com.example.ujianakhir.ui.view.JenisHewan.DestinasiEntryJenisHewan
import com.example.ujianakhir.ui.view.JenisHewan.DestinasiHomeJenisHewan
import com.example.ujianakhir.ui.view.JenisHewan.DetailJenisHewanScreen
import com.example.ujianakhir.ui.view.JenisHewan.EntryJnsHwnScreen
import com.example.ujianakhir.ui.view.JenisHewan.HomeScreenJenisHewan
import com.example.ujianakhir.ui.view.JenisHewan.UpdateJenisHewanScreen
import com.example.ujianakhir.ui.view.Perawatan.DestinasiDetailPerawatan
import com.example.ujianakhir.ui.view.Perawatan.DestinasiEntryPerawatan
import com.example.ujianakhir.ui.view.Perawatan.DestinasiHomePerawatan
import com.example.ujianakhir.ui.view.Perawatan.DetailPerawatanScreen
import com.example.ujianakhir.ui.view.Perawatan.EntryPrwtScreen
import com.example.ujianakhir.ui.view.Perawatan.HomeScreenPerawatan
import com.example.ujianakhir.ui.view.Perawatan.UpdatePerawatanScreen


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiMenu.route,
        modifier = Modifier
    ){

        //Menu Utama
        composable(
            route = DestinasiMenu.route) {
            MenuScreen(
                onNavigateToHewan = { navController.navigate(DestinasiHomeHewan.route) },
                onNavigateToDokter = { navController.navigate(DestinasiHomeDokter.route) },
                onNavigateToJenisHewan = { navController.navigate(DestinasiHomeJenisHewan.route) },
                onNavigateToPerawatan = { navController.navigate(DestinasiHomePerawatan.route) },
                modifier = Modifier
            )
        }


        //Hewan
        composable(DestinasiHomeHewan.route){
            HomeScreenHewan(
                navigateBack = { navController.navigate(DestinasiMenu.route) },
                navigateToItemEntry = {navController.navigate(DestinasiEntryHewan.route)},
                onDetailClick = { idhewan ->
                    navController.navigate("${DestinasiDetailHewan.route}/$idhewan")
                }
            )
        }
        composable(DestinasiEntryHewan.route){
            EntryHwnScreen(navigateBack = {
                navController.navigate(DestinasiHomeHewan.route){
                    popUpTo(DestinasiHomeHewan.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = "${DestinasiDetailHewan.route}/{idhewan}",
            arguments = listOf(navArgument("idhewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idhewan = backStackEntry.arguments?.getString("idhewan")
            DetailHewanScreen(
                idhewan = idhewan ?: return@composable,
                navigateBack = { navController.navigateUp()},
                navigateToEntryPrwt = {navController.navigate(DestinasiEntryPerawatan.route)},
                navController = navController
            )
        }
        composable(
            route = "updateHewan/{idhewan}",
            arguments = listOf(navArgument("idhewan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idhewan = backStackEntry.arguments?.getString("idhewan")
            UpdateHewanScreen(
                idhewan = idhewan ?: return@composable,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        //Dokter
        composable(DestinasiHomeDokter.route){
            HomeScreenDokter(
                navigateBackDktr = { navController.navigate(DestinasiMenu.route) },
                navigateToItemEntry = {navController.navigate(DestinasiEntryDokter.route)},
                onDetailClick = { iddokter ->
                    navController.navigate("${DestinasiDetailDokter.route}/$iddokter")
                }
            )
        }
        composable(DestinasiEntryDokter.route){
            EntryDktrScreen(navigateBack = {
                navController.navigate(DestinasiHomeDokter.route){
                    popUpTo(DestinasiHomeDokter.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = "${DestinasiDetailDokter.route}/{iddokter}",
            arguments = listOf(navArgument("iddokter") { type = NavType.StringType })
        ) { backStackEntry ->
            val iddokter = backStackEntry.arguments?.getString("iddokter")
            DetailDokterScreen(
                iddokter = iddokter ?: return@composable,
                navigateBack = { navController.navigateUp()},
                navController = navController
            )
        }
        composable(
            route = "updateDokter/{iddokter}",
            arguments = listOf(navArgument("iddokter") { type = NavType.StringType })
        ) { backStackEntry ->
            val iddokter = backStackEntry.arguments?.getString("iddokter")
            UpdateDokterScreen(
                iddokter = iddokter ?: return@composable,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }


        //Jenis Hewan
        composable(DestinasiHomeJenisHewan.route){
            HomeScreenJenisHewan(
                navigateBackJenis = { navController.navigate(DestinasiMenu.route) },
                navigateToItemEntry = {navController.navigate(DestinasiEntryJenisHewan.route)},
                onDetailClick = {jenishewanid ->
                    navController.navigate("${DestinasiDetailJenisHewan.route}/$jenishewanid")
                }
            )
        }

        composable(DestinasiEntryJenisHewan.route){
            EntryJnsHwnScreen(navigateBack = {
                navController.navigate(DestinasiHomeJenisHewan.route){
                    popUpTo(DestinasiHomeJenisHewan.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            route = "${DestinasiDetailJenisHewan.route}/{jenishewanid}",
            arguments = listOf(navArgument("jenishewanid") { type = NavType.StringType })
        ) { backStackEntry ->
            val jenishewanid = backStackEntry.arguments?.getString("jenishewanid")
            DetailJenisHewanScreen(
                jenishewanid = jenishewanid ?: return@composable,
                navigateBack = { navController.navigateUp()},
                navController = navController
            )
        }
        composable(
            route = "updateJenisHewan/{jenishewanid}",
            arguments = listOf(navArgument("jenishewanid") { type = NavType.StringType })
        ) { backStackEntry ->
            val jenishewanid = backStackEntry.arguments?.getString("jenishewanid")
            UpdateJenisHewanScreen(
                jenishewanid = jenishewanid ?: return@composable,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        //Perawatan
        composable(DestinasiHomePerawatan.route){
            HomeScreenPerawatan(
                navigateBack = { navController.navigate(DestinasiMenu.route) },
                navigateBackPerawat = { navController.navigate(DestinasiMenu.route) },
                onDetailClick = {idperawatan ->
                    navController.navigate("${DestinasiDetailPerawatan.route}/$idperawatan")

                }
            )
        }

        composable(DestinasiEntryPerawatan.route){
            EntryPrwtScreen(
                navigateBack = {navController.navigate(DestinasiHomeHewan.route){
                    popUpTo(DestinasiHomeHewan.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(
            route = "${DestinasiDetailPerawatan.route}/{idperawatan}",
            arguments = listOf(navArgument("idperawatan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idperawatan = backStackEntry.arguments?.getString("idperawatan")
            DetailPerawatanScreen(
                idperawatan = idperawatan ?: return@composable,
                navigateBack = { navController.navigateUp()},
                navController = navController
            )
        }
        composable(
            route = "updatePerawatan/{idperawatan}",
            arguments = listOf(navArgument("idperawatan") { type = NavType.StringType })
        ) { backStackEntry ->
            val idperawatan = backStackEntry.arguments?.getString("idperawatan")
            UpdatePerawatanScreen(
                idperawatan = idperawatan ?: return@composable,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}