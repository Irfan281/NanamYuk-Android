package com.irfan.nanamyuk.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.nanamyuk.R
import com.irfan.nanamyuk.databinding.ItemStatusBinding
import com.irfan.nanamyuk.ui.detail.DetailActivity

class UserPlantsAdapter(private val datas: List<String>) :
    RecyclerView.Adapter<UserPlantsAdapter.ViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStatusBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = datas[position]

        holder.binding.textView.text = name

        holder.binding.card.setOnClickListener(this)
        holder.binding.fabWater.setOnClickListener(this)
    }

    override fun getItemCount(): Int = datas.size

    inner class ViewHolder(var binding: ItemStatusBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(view: View) {
        when(view.id){
            R.id.fab_water -> {
                Toast.makeText(view.context, "Ini tombol water", Toast.LENGTH_SHORT).show()
            }R.id.card -> {
                val i = Intent(view.context, DetailActivity::class.java)
                view.context.startActivity(i)
            }
        }
    }

}
