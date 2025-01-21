package com.example.ujianakhir.repository

import com.example.ujianakhir.service.HewanService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val hewanRepository: HewanRepository
}

class Container: AppContainer {
    private val baseUrl = "http://10.0.2.2/ucpHewan/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val hewanService: HewanService by lazy {retrofit.create(HewanService::class.java)}
    override val hewanRepository: HewanRepository by lazy {NetworkHewanRepository(hewanService)}

}
