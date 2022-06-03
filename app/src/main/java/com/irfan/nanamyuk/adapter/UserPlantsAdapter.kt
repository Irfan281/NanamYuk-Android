package com.irfan.nanamyuk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.nanamyuk.databinding.ItemStatusBinding
import com.irfan.nanamyuk.ui.detail.DetailActivity

class UserPlantsAdapter(private val datas: List<String>) :
    RecyclerView.Adapter<UserPlantsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStatusBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = datas[position]

        holder.binding.textView.text = name

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(var binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root)

}
