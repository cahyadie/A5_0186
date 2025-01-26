package com.example.ujianakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class JenisHewan(
    @SerialName("jenis_hewan_id") val jenishewanid: String,
    @SerialName("nama_jenis_hewan") val namajenishewan: String,
    val deskripsi: String,
)
