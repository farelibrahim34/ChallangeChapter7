package com.example.logindatastorefix.isi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    fun dataBookmark(){
        viewModel = ViewModelProvider(this).get(ViewModelDataMhs::class.java)
        viewModel.getBookmarkDataMhs().observe(this,{
            if (it != null){

                binding.rvBookmark.layoutManager = LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL,false)
                val adapter = BookmarkAdapter(it)
                binding.rvBookmark.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.callBookmarkDataMhs()
    }
}