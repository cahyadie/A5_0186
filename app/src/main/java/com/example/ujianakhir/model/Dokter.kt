package com.example.ujianakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Dokter(
    @SerialName("id_dokter") val iddokter: String,
    @SerialName("nama_dokter") val namadokter: String,
    val spesialisasi: String,
    val kontak: String,
)

@Serializable
data class DokterDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Dokter
)

@Serializable
data class DokterResponse(
    val status: Boolean,
    val message: String,
    val data: List<Dokter>
)