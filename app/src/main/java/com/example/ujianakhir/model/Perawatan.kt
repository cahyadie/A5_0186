package com.example.ujianakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class Perawatan(
    @SerialName("id_perawatan") val idperawatan: String,
    @SerialName("id_hewan") val idhewan: String,
    @SerialName("id_dokter") val iddokter: String,
    @SerialName("tanggal_perawatan") val tanggalperawatan: String,
    @SerialName("detail_perawatan") val detailperawatan: String,
)


@Serializable
data class PerawatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Perawatan
)

@Serializable
data class PerawatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Perawatan>
)
