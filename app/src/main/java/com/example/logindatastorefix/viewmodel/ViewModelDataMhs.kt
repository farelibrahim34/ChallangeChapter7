package com.example.logindatastorefix.viewmodel

import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logindatastorefix.model.*
import com.example.logindatastorefix.network.APIInterface
import com.example.logindatastorefix.network.APIMahasiswa
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelDataMhs @Inject constructor(var api : APIInterface) : ViewModel() {
    lateinit var liveDataMhs : MutableLiveData<List<ResponseDataMhsItem>?>
    lateinit var ldMhsById : MutableLiveData<ResponseDataMhsItem?>
    lateinit var postLdDataMhs : MutableLiveData<ResponseDataMhs?>
    lateinit var editLdDataMhs : MutableLiveData<ResponseDataMhsItem?>
    lateinit var deleteLdDataMhs : MutableLiveData<ResponseDataMhsItem?>
    lateinit var liveDataBookmark : MutableLiveData<List<ResponseBookmarkItem>>
    lateinit var postBookmark : MutableLiveData<ResponseBookmark>
    lateinit var ldBookmarkById : MutableLiveData<ResponseBookmarkItem>
    lateinit var deleteLdBookmark : MutableLiveData<ResponseBookmarkItem>

    init {
        liveDataMhs = MutableLiveData()
        ldMhsById = MutableLiveData()
        postLdDataMhs = MutableLiveData()
        editLdDataMhs = MutableLiveData()
        deleteLdDataMhs = MutableLiveData()
        liveDataBookmark = MutableLiveData()
        postBookmark = MutableLiveData()
        ldBookmarkById = MutableLiveData()
        deleteLdBookmark = MutableLiveData()

    }
    //    null
    fun getDataMhs(): MutableLiveData<List<ResponseDataMhsItem>?> {
        return liveDataMhs
    }
    fun getDataMhs(id : Int): MutableLiveData<ResponseDataMhsItem?> {
        return ldMhsById
    }
    fun postDataMhs(): MutableLiveData<ResponseDataMhs?> {
        return postLdDataMhs
    }
    fun editDataMhs(): MutableLiveData<ResponseDataMhsItem?> {
        return editLdDataMhs
    }
    fun getLdDelDataMhs(): MutableLiveData<ResponseDataMhsItem?> {
        return deleteLdDataMhs
    }
    fun getBookmarkDataMhs(): MutableLiveData<List<ResponseBookmarkItem>> {
        return liveDataBookmark
    }
    fun postDataBookmark(): MutableLiveData<ResponseBookmark>{
        return postBookmark
    }
    fun getBookmarkById(id : Int): MutableLiveData<ResponseBookmarkItem>{
        return ldBookmarkById
    }
    fun getDelBookmark(): MutableLiveData<ResponseBookmarkItem>{
        return deleteLdBookmark
    }




    fun callDeleteData(id : Int){
        api.deleteDataMhs(id)
            .enqueue(object : Callback<ResponseDataMhsItem> {
                override fun onResponse(
                    call: Call<ResponseDataMhsItem>,
                    response: Response<ResponseDataMhsItem>
                ) {
                    if (response.isSuccessful){
                        deleteLdDataMhs.postValue(response.body())
                    }else{
                        deleteLdDataMhs.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataMhsItem>, t: Throwable) {
                    deleteLdDataMhs.postValue(null)
                }

            })
    }
    fun callDeleteBookmark(id: Int){
        api.deleteBookmark(id)
            .enqueue(object : Callback<ResponseBookmarkItem>{
                override fun onResponse(
                    call: Call<ResponseBookmarkItem>,
                    response: Response<ResponseBookmarkItem>
                ) {
                    if (response.isSuccessful){
                        deleteLdBookmark.postValue(response.body())
                    }else{
                        deleteLdBookmark.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseBookmarkItem>, t: Throwable) {
                    deleteLdBookmark.postValue(null)
                }

            })
    }

    fun editApiDataMhs(id: Int,nama : String,nim : String,alamat : String,foto : String,jk : String){
        api.editDataMhs(id, DataMahasiswa(nama,nim,alamat,foto,jk))
            .enqueue(object : Callback<ResponseDataMhsItem> {
                override fun onResponse(
                    call: Call<ResponseDataMhsItem>,
                    response: Response<ResponseDataMhsItem>
                ) {
                    if (response.isSuccessful){
                        editLdDataMhs.postValue(response.body())
                    }else{
                        editLdDataMhs.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataMhsItem>, t: Throwable) {
                    editLdDataMhs.postValue(null)
                }

            })

    }

    fun callPostApiDataMhs(nama : String, nim : String, jk : String, alamat : String, foto : String){
        api.addDataMhs(DataMahasiswa(nama,nim,jk,alamat,foto))
            .enqueue(object  : Callback<ResponseDataMhs> {
                override fun onResponse(
                    call: Call<ResponseDataMhs>,
                    response: Response<ResponseDataMhs>
                ) {
                    if (response.isSuccessful){
                        postLdDataMhs.postValue(response.body())
                    }else{
                        postLdDataMhs.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataMhs>, t: Throwable) {
                    postLdDataMhs.postValue(null)
                }

            })
    }
    fun callAddBookmark(nama : String, nim : String, jk : String, alamat : String, foto : String){
        api.addBookmarkDataMhs(DataMahasiswa(nama,nim,jk,alamat,foto))
            .enqueue(object : Callback<ResponseBookmark>{
                override fun onResponse(
                    call: Call<ResponseBookmark>,
                    response: Response<ResponseBookmark>
                ) {
                    if (response.isSuccessful){
                        postBookmark.postValue(response.body())
                    }else{
                        postBookmark.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseBookmark>, t: Throwable) {
                    postBookmark.postValue(null)
                }

            })
    }


    fun callGetDataMhs(id : Int){
        api.getDataByid(id)
            .enqueue(object : Callback<ResponseDataMhsItem> {
                override fun onResponse(
                    call: Call<ResponseDataMhsItem>,
                    response: Response<ResponseDataMhsItem>
                ) {
                    if(response.isSuccessful){
                        ldMhsById.postValue(response.body())
                    }else{
                        ldMhsById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataMhsItem>, t: Throwable) {
                    ldMhsById.postValue(null)
                }

            })
    }
    fun callGetBookmark(id: Int){
        api.getBookmarkByid(id)
            .enqueue(object : Callback<ResponseBookmarkItem>{
                override fun onResponse(
                    call: Call<ResponseBookmarkItem>,
                    response: Response<ResponseBookmarkItem>
                ) {
                    if (response.isSuccessful){
                        ldBookmarkById.postValue(response.body())
                    }else{
                        ldBookmarkById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseBookmarkItem>, t: Throwable) {
                    ldBookmarkById.postValue(null)
                }

            })
    }

    fun callApiDataMhs(){
        api.getAllDataMhs()
            .enqueue(object : Callback<List<ResponseDataMhsItem>> {
                override fun onResponse(
                    call: Call<List<ResponseDataMhsItem>>,
                    response: Response<List<ResponseDataMhsItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataMhs.postValue(response.body())
                    }else{
                        liveDataMhs.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataMhsItem>>, t: Throwable) {
                    liveDataMhs.postValue(null)
                }

            })
    }
    fun callBookmarkDataMhs(){
        api.getBookmarkMhs()
            .enqueue(object : Callback<List<ResponseBookmarkItem>>{
                override fun onResponse(
                    call: Call<List<ResponseBookmarkItem>>,
                    response: Response<List<ResponseBookmarkItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataBookmark.postValue(response.body())
                    }else{
                        liveDataBookmark.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseBookmarkItem>>, t: Throwable) {
                    liveDataBookmark.postValue(null)
                }

            })
    }




}