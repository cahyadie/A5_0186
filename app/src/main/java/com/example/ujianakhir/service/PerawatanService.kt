package com.example.ujianakhir.service

import com.example.ujianakhir.model.Perawatan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PerawatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getperawatan.php")
    suspend fun getperawatan(): List<Perawatan>

    @GET("get1perawatan.php")
    suspend fun getPerawatanById(@Query("id_perawatan") id_perawatan: String): Perawatan

    @POST("insertPerawatan.php")
    suspend fun insertPerawatan(@Body perawatan: Perawatan)

    @PUT("editperawatan.php")
    suspend fun updatePerawatan(@Query("id_perawatan") id_perawatan: String, @Body perawatan: Perawatan)

    @DELETE("deleteperawatan.php")
    suspend fun deletePerawatan(@Query("id_perawatan") id_perawatan: String): retrofit2.Response<Void>
}