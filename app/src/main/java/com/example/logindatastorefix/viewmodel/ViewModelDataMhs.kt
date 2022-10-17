package com.example.logindatastorefix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.logindatastorefix.model.DataMahasiswa
import com.example.logindatastorefix.model.ResponseDataMhs
import com.example.logindatastorefix.model.ResponseDataMhsItem
import com.example.logindatastorefix.network.APIMahasiswa
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelDataMhs : ViewModel() {
    lateinit var liveDataMhs : MutableLiveData<List<ResponseDataMhsItem>?>
    lateinit var ldMhsById : MutableLiveData<ResponseDataMhsItem?>
    lateinit var postLdDataMhs : MutableLiveData<ResponseDataMhs?>
    lateinit var editLdDataMhs : MutableLiveData<ResponseDataMhsItem?>
    lateinit var deleteLdDataMhs : MutableLiveData<ResponseDataMhsItem?>

    init {
        liveDataMhs = MutableLiveData()
        ldMhsById = MutableLiveData()
        postLdDataMhs = MutableLiveData()
        editLdDataMhs = MutableLiveData()
        deleteLdDataMhs = MutableLiveData()

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

    fun callDeleteData(id : Int){
        APIMahasiswa.instance.deleteDataMhs(id)
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

    fun editApiDataMhs(id: Int,nama : String,nim : String,alamat : String,foto : String,jk : String){
        APIMahasiswa.instance.editDataMhs(id, DataMahasiswa(nama,nim,alamat,foto,jk))
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
        APIMahasiswa.instance.addDataMhs(DataMahasiswa(nama,nim,jk,alamat,foto))
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


    fun callGetDataMhs(id : Int){
        APIMahasiswa.instance.getDataByid(id)
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

    fun callApiDataMhs(){
        APIMahasiswa.instance.getAllDataMhs()
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




}