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
