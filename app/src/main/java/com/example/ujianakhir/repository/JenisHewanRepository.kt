package com.example.ujianakhir.repository

import com.example.ujianakhir.model.JenisHewan
import com.example.ujianakhir.service.JenisHewanService
import java.io.IOException


interface JenisHewanRepository{
    suspend fun getJenisHewan(): List<JenisHewan>
    suspend fun insertJenisHewan(jenisHewan: JenisHewan)
    suspend fun updateJenisHewan(jenishewanid: String, jenisHewan: JenisHewan)
    suspend fun deleteJenisHewan(jenishewanid: String)
    suspend fun getJenisHewanById(jenishewanid: String): JenisHewan
}

class NetworkJenisHewanRepository(
    private val jenisHewanApiService: JenisHewanService
) : JenisHewanRepository{
    override suspend fun insertJenisHewan(jenisHewan: JenisHewan) {
        jenisHewanApiService.insertJenisHewan(jenisHewan)
    }


    override suspend fun updateJenisHewan(jenishewanid: String, jenisHewan: JenisHewan) {
        jenisHewanApiService.updateJenisHewan(jenishewanid,jenisHewan)
    }

    override suspend fun deleteJenisHewan(jenishewanid: String) {
        try {
            val response = jenisHewanApiService.deleteJenisHewan(jenishewanid)
            if (!response.isSuccessful){
                throw IOException("Failed to delete JenisHewan. HTTP Status code: ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getJenisHewan(): List<JenisHewan> = jenisHewanApiService.getJenisHewan()
    override suspend fun getJenisHewanById(jenishewanid: String): JenisHewan {
        return jenisHewanApiService.getJenisHewanById(jenishewanid)
    }

}