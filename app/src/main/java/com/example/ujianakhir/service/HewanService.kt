package com.example.ujianakhir.service

import com.example.ujianakhir.model.Hewan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface HewanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("gethewan.php")
    suspend fun getHewan(): List<Hewan>

    @GET("get1hewan.php")
    suspend fun getHewanById(@Query("id_hewan") idhewan: String): Hewan

    @POST("inserthewan.php")
    suspend fun insertHewan(@Body hewan: Hewan)

    @PUT("edithewan.php")
    suspend fun updateHewan(@Query("id_hewan") id_hewan: String, @Body hewan: Hewan)

    @DELETE("deletehewan.php")
    suspend fun deleteHewan(@Query("id_hewan") id_hewan: String): retrofit2.Response<Void>
}