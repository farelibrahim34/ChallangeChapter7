package com.example.logindatastorefix.isi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.logindatastorefix.R
import com.example.logindatastorefix.databinding.ActivityDetailBinding
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs
import kotlin.properties.Delegates

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    lateinit var viewModel : ViewModelDataMhs
    var id by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        id = intent.getStringExtra("id")!!.toInt()
        Log.d("DEBUG_ID",id.toString())
        setDetail()


    }
    fun setDetail(){

        viewModel.callGetDataMhs(id)
        viewModel.getDataMhs(id).observe(this,{
            binding.detailNama.setText("Nama : "+ it!!.nama)
            binding.detailNim.setText("Nim : "+ it!!.nim)
            binding.detailJk.setText("Jenis Kelamin : "+ it!!.jk)
            binding.detailAlamat.setText("Alamat : "+ it!!.alamat)
            var url = it.foto
            Glide.with(this).load(url).circleCrop().into(binding.imgDetail)



        })
    }
}