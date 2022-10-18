package com.example.logindatastorefix

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logindatastorefix.view.MhsAdapter
import com.example.logindatastorefix.databinding.ActivityMainBinding
import com.example.logindatastorefix.isi.AddActivity
import com.example.logindatastorefix.isi.FavoriteActivity
import com.example.logindatastorefix.isi.ProfileActivity
import com.example.logindatastorefix.view.LoginActivity
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var dataLogin : DataStoreLogin
    lateinit var viewModel :ViewModelDataMhs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataLogin = DataStoreLogin(this)
        dataLogin.userName.asLiveData().observe(this,{
            binding.txtUsername.text = "Hello, "+ it.toString()
        })

        binding.btnProfile.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java))
        }
        binding.btnKeBookmark.setOnClickListener {
            startActivity(Intent(this,FavoriteActivity::class.java))
        }

        dataMhs()




    }
    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setTitle("Tutup Aplikasi")
            .setMessage("Yakin tutup dari aplikasi?")
            .setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                finishAffinity()
            }
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .show()


    }
    fun dataMhs(){

        viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        viewModel.getDataMhs().observe(this,{
            if (it != null){
                binding.progressBar.visibility = View.GONE
                binding.rvList.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false)
                val adapter = MhsAdapter(it)
                binding.rvList.adapter = adapter
                adapter.onDeleteClick ={
                    deleteDataMhs(it.id.toInt())
                }
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.callApiDataMhs()
    }
    fun deleteDataMhs(id : Int){
        viewModel.callDeleteData(id)
        viewModel.getLdDelDataMhs().observe(this,{
            if (it != null){
                dataMhs()
                Toast.makeText(this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
        })
    }

}