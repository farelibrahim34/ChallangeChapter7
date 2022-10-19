package com.example.logindatastorefix.isi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.databinding.ActivityBookmarkBinding
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class BookmarkActivity : AppCompatActivity() {
    lateinit var binding : ActivityBookmarkBinding
    lateinit var viewModel : ViewModelDataMhs
    var id by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        id = intent.getStringExtra("id")!!.toInt()

        setDetail()
        binding.btnOke.setOnClickListener {
            val nama = binding.bmNama.text.toString()
            val nim = binding.bmNim.text.toString()
            val alamat = binding.bmAlamat.text.toString()
            val foto = binding.bmFoto.text.toString()
            val jk = binding.bmJk.text.toString()

            addBookmark(nama,nim,alamat,foto,jk)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"add bookmark sukses", Toast.LENGTH_SHORT).show()

        }

    }
    fun setDetail(){

        viewModel.callGetDataMhs(id)
        viewModel.getDataMhs(id).observe(this,{
            binding.bmNama.setText(it!!.nama)
            binding.bmNim.setText(it!!.nim)
            binding.bmJk.setText(it!!.jk)

//            var url = it.foto
//            Glide.with(this).load(url).circleCrop().into(binding.imgDetail)

            binding.bmAlamat.setText(it.alamat)
            binding.bmFoto.setText(it.foto)

        })
    }

    fun addBookmark(nama : String, nim : String, jk : String, alamat : String, foto : String){
        val viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        viewModel.callAddBookmark(nama,nim,jk,alamat,foto)
        viewModel.postDataBookmark().observe(this,{
            Toast.makeText(this,"add bookmark sukses", Toast.LENGTH_SHORT).show()
        })
        finish()
    }
}