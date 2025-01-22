package com.example.ujianakhir.service

import com.example.ujianakhir.model.Dokter
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DokterService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("getdokter.php")
    suspend fun getDokter(): List<Dokter>

    @GET("get1dokter.php")
    suspend fun getDokterById(@Query("id_dokter") id_dokter: String): Dokter

    @POST("insertdokter.php")
    suspend fun insertDokter(@Body dokter: Dokter)

    @PUT("editdokter.php")
    suspend fun updateDokter(@Query("id_dokter") id_dokter: String, @Body dokter: Dokter)

    @DELETE("deletedokter.php")
    suspend fun deleteDokter(@Query("id_dokter") id_dokter: String): retrofit2.Response<Void>
}