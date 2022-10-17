package com.example.logindatastorefix.model


import com.google.gson.annotations.SerializedName

data class ResponseDataMhsItem(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("foto")
    val foto: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("jk")
    val jk: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("nim")
    val nim: String
)