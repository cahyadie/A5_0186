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


