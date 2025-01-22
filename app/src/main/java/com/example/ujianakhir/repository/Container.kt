package com.example.ujianakhir.repository

import com.example.ujianakhir.service.DokterService
import com.example.ujianakhir.service.HewanService
import com.example.ujianakhir.service.JenisHewanService
import com.example.ujianakhir.service.PerawatanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val hewanRepository: HewanRepository
    val dokterRepository: DokterRepository
    val jenisHewanRepository: JenisHewanRepository
    val perawatanRepository: PerawatanRepository
}

class Container: AppContainer {
    private val baseUrl = "http://10.0.2.2/ucpHewan/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy {retrofit.create(HewanService::class.java)}
    override val hewanRepository: HewanRepository by lazy {NetworkHewanRepository(hewanService)}

    private val dokterService: DokterService by lazy {retrofit.create(DokterService::class.java)}
    override val dokterRepository: DokterRepository by lazy {NetworkDokterRepository(dokterService)}

    private val jenisHewanService: JenisHewanService by lazy {retrofit.create(JenisHewanService::class.java)}
    override val jenisHewanRepository: JenisHewanRepository by lazy {NetworkJenisHewanRepository(jenisHewanService)}

    private val perawatanService: PerawatanService by lazy {retrofit.create(PerawatanService::class.java)}
    override val perawatanRepository: PerawatanRepository by lazy {NetworkPerawatanRepository(perawatanService)}

}
