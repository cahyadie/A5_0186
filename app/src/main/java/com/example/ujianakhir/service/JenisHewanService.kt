package com.example.ujianakhir.service

import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.model.JenisHewan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface JenisHewanService {
    @Headers(
    "Accept: application/json",
    "Content-Type: application/json"
    )
    @GET("getjenishewan.php")
    suspend fun getJenisHewan(): List<JenisHewan>

    @GET("get1jenishewan.php")
    suspend fun getJenisHewanById(@Query("jenis_hewan_id") jenis_hewan_id: String): JenisHewan

    @POST("insertjenishewan.php")
    suspend fun insertJenisHewan(@Body jenisHewan: JenisHewan)

    @PUT("editjenishewan.php")
    suspend fun updateJenisHewan(@Query("jenis_hewan_id") jenis_hewan_id: String, @Body jenisHewan: JenisHewan)

    @DELETE("deletejenishewan.php")
    suspend fun deleteJenisHewan(@Query("jenis_hewan_id") jenis_hewan_id: String): retrofit2.Response<Void>
}