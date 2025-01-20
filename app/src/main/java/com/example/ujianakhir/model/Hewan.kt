package com.example.ujianakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    @SerialName("id_hewan") val idhewan: String,
    @SerialName("nama_hewan") val namahewan: String,
    @SerialName("jenis_id_hewan") val jenisidhewan: String,
    val pemilik: String,
    @SerialName("kontak_peilik") val kontakpemilik: String,
    @SerialName("tanggal_lahir") val tanggallahir: String,
    @SerialName("catatan_kesehatan") val catatankesehatan: String,
)

@Serializable
data class HewanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Hewan
)

@Serializable
data class HewanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Hewan>
)