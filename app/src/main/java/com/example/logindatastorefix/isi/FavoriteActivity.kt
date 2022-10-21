package com.example.logindatastorefix.isi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.logindatastorefix.R
import com.example.logindatastorefix.databinding.ActivityFavoriteBinding
import com.example.logindatastorefix.databinding.ActivityProfileBinding
import com.example.logindatastorefix.view.BookmarkAdapter
import com.example.logindatastorefix.view.MhsAdapter
import com.example.logindatastorefix.viewmodel.ViewModelDataMhs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    lateinit var viewModel : ViewModelDataMhs
    lateinit var binding : ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataBookmark()


    }
//    fun dataBookmark(){
//
//        viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
//        viewModel.getBookmarkDataMhs().observe(this,{
//            if (it != null){
//                binding.progressBarBm.visibility = View.GONE
//
//                binding.rvBookmark.layoutManager = LinearLayoutManager(this,
//                    LinearLayoutManager.VERTICAL,false)
//                val adapter = BookmarkAdapter(it)
//                binding.rvBookmark.adapter = adapter
//                adapter.onDeleteClick ={
//                    deleteBookmark(it.id.toInt())
//                }
//                adapter.notifyDataSetChanged()
//            }
//        })
//        viewModel.callBookmarkDataMhs()
//    }
//    fun deleteBookmark(id : Int){
//        viewModel.callDeleteBookmark(id)
//        viewModel.getDelBookmark().observe(this,{
//            if (it != null){
//                dataBookmark()
//                Toast.makeText(this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
fun dataBookmark(){

    viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
    viewModel.getBookmarkDataMhs().observe(this,{
        if (it != null){
            binding.progressBarBm.visibility = View.GONE
            binding.rvBookmark.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false)
            val adapter = BookmarkAdapter(it)
            binding.rvBookmark.adapter = adapter
            adapter.onDeleteClick ={
                deleteBookmarl(it.id.toInt())
            }
            adapter.notifyDataSetChanged()
        }
    })
    viewModel.callBookmarkDataMhs()
}
    fun deleteBookmarl(id : Int){
        viewModel.callDeleteBookmark(id)
        viewModel.getDelBookmark().observe(this,{
            if (it != null){
                dataBookmark()
                Toast.makeText(this,"Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
        })
    }
}