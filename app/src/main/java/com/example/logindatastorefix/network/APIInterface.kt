package com.example.logindatastorefix.network

import com.example.logindatastorefix.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @GET("datamhs")
    fun getAllDataMhs() : Call<List<ResponseDataMhsItem>>

    @GET("datamhs/{id}")
    fun getDataByid(@Path("id") id : Int): Call<ResponseDataMhsItem>

    @PUT("datamhs/{id}")
    fun editDataMhs(@Path("id") id: Int, @Body request : DataMahasiswa): Call<ResponseDataMhsItem>

    @POST("datamhs")
    fun addDataMhs(@Body request : DataMahasiswa): Call<ResponseDataMhs>

    @DELETE("datamhs/{id}")
    fun deleteDataMhs(@Path("id")id : Int): Call<ResponseDataMhsItem>

    @POST("bookmark")
    fun addBookmarkDataMhs(@Body request : DataMahasiswa): Call<ResponseBookmark>

    @GET("bookmark")
    fun getBookmarkMhs() : Call<List<ResponseBookmarkItem>>

    @GET("bookmark")
    fun getBookmarkByid(@Path("id") id : Int): Call<ResponseBookmarkItem>

    @DELETE("bookmark/{id}")
    fun deleteBookmark(@Path("id")id : Int): Call<ResponseBookmarkItem>
}