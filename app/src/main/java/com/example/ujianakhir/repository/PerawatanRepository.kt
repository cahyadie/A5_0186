package com.example.ujianakhir.repository

import com.example.ujianakhir.model.Hewan
import com.example.ujianakhir.model.Perawatan
import com.example.ujianakhir.service.PerawatanService
import java.io.IOException

interface PerawatanRepository{
    suspend fun getPerawatan(): List<Perawatan>
    suspend fun insertPerawatan(perawatan: Perawatan)
    suspend fun updatePerawatan(idperawatan: String, perawatan: Perawatan)
    suspend fun deletePerawatan(idperawatan: String)
    suspend fun getPerawatanById(idperawatan: String): Perawatan
}

class NetworkPerawatanRepository(
    private val perawatanApiService: PerawatanService
) : PerawatanRepository{
    override suspend fun insertPerawatan(perawatan: Perawatan) {
        perawatanApiService.insertPerawatan(perawatan)
    }


    override suspend fun updatePerawatan(idperawatan: String, perawatan: Perawatan) {
        perawatanApiService.updatePerawatan(idperawatan,perawatan)
    }

    override suspend fun deletePerawatan(idperawatan: String) {
        try {
            val response = perawatanApiService.deletePerawatan(idperawatan)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Perawatan. HTTP Status code: ${response.code()}")
            }else{
                response.message()
                println(response.message())
            }
        }catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPerawatan(): List<Perawatan> = perawatanApiService.getperawatan()
    override suspend fun getPerawatanById(idperawatan: String): Perawatan {
        return perawatanApiService.getPerawatanById(idperawatan)
    }

}