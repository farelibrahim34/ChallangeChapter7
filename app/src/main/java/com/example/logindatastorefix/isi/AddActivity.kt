package com.example.logindatastorefix.isi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.R
import com.example.logindatastorefix.databinding.ActivityAddBinding
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs
import com.example.logindatastorefix.workmanager.Worker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
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

            val wManager = OneTimeWorkRequestBuilder<Worker>()
                .setInitialDelay(20, TimeUnit.SECONDS)
                .setInputData(
                    workDataOf(
                    "title" to "NOTIFIKASI!!!",
                    "message" to "DATA MAHASISWA TELAH DIBUAT")
                ).build()
            WorkManager.getInstance(this).enqueue(wManager)

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