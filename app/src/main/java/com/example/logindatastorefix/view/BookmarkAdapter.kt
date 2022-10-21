package com.example.logindatastorefix.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.logindatastorefix.databinding.ItemBinding
import com.example.logindatastorefix.model.ResponseBookmarkItem
import com.example.logindatastorefix.model.ResponseDataMhsItem

class BookmarkAdapter(var listBookmark : List<ResponseBookmarkItem>):RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {
    var onDeleteClick : ((ResponseBookmarkItem)-> Unit)? = null
    class ViewHolder(var binding : ItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v= ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtNama.text =   ("Nama               : "+listBookmark[position].nama)
        holder.binding.txtNim.text =    ("Nim                   : "+listBookmark[position].nim)
        holder.binding.txtJk.text =     ("Jenis Kelamin : "+listBookmark[position].jk)
        holder.binding.txtAlamat.text = ("Alamat              : "+listBookmark[position].alamat)
        holder.binding.imgDelete.setOnClickListener {
            onDeleteClick?.invoke(listBookmark[position])
        }

        Glide.with(holder.itemView).load(listBookmark[position].foto).circleCrop().into(holder.binding.imgMhs)
    }

    override fun getItemCount(): Int {
        return listBookmark.size
    }
}