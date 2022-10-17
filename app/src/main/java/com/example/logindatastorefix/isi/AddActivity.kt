package com.example.logindatastorefix.isi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.R
import com.example.logindatastorefix.databinding.ActivityAddBinding
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs

class AddActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambahData.setOnClickListener {
            val nama = binding.tambahNama.text.toString()
            val nim = binding.tambahNim.text.toString()
            val alamat = binding.tmbhAlamat.text.toString()
            val foto = binding.tmbhFoto.text.toString()
            val jk = binding.tmbhJk.text.toString()

            addData(nama,nim,alamat,foto,jk)
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this,"add data sukses", Toast.LENGTH_SHORT).show()

        }

    }
    fun addData(nama : String, nim : String, jk : String, alamat : String, foto : String){
        val viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        viewModel.callPostApiDataMhs(nama,nim,jk,alamat,foto)
        viewModel.postDataMhs().observe(this,{
            if (it != null){
                Toast.makeText(this,"add data sukses", Toast.LENGTH_SHORT).show()
            }
        })
        finish()
    }
}