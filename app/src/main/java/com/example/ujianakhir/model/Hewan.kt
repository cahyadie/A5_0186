package com.example.ujianakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    @SerialName("id_hewan") val idhewan: String,
    @SerialName("nama_hewan") val namahewan: String,
    @SerialName("jenis_hewan_id") val jenishewanid: String,
    @SerialName("kontak_pemilik") val kontakpemilik: String,
    @SerialName("tanggal_lahir") val tanggallahir: String,
    val pemilik: String,
    @SerialName("catatan_kesehatan") val catatankesehatan: String,
)