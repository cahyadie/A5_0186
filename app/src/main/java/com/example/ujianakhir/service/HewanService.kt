package com.example.ujianakhir.service

import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.model.HewanDetailResponse
import com.example.ujianakhir.model.HewanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface HewanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET(".")
    suspend fun getHewan(): HewanResponse

    @GET("{idhewan}")
    suspend fun getHewanById(@Path("idhewan") idhewan: String): HewanDetailResponse

    @POST("store")
    suspend fun insertHewan(@Body hewan: Hewan)

    @PUT("{idhewan}")
    suspend fun updateHewan(@Path("idhewan") idhewan: String, @Body hewan: Hewan)

    @DELETE("{idhewan}")
    suspend fun deleteHewan(@Path("idhewan") idhewan: String): Response<Void>
}