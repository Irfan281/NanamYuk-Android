package com.irfan.nanamyuk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.nanamyuk.R
import com.irfan.nanamyuk.data.Tanah

class TanahAdapter (context: Context, private var datas: List<Tanah>) :
    RecyclerView.Adapter<TanahAdapter.SingleViewHolder>() {

    private var selected = -1

    private var onClick: OnItemClickListener? = null

    fun setOnItemClickLitener(mOnItemClickListener: OnItemClickListener) {
        this.onClick = mOnItemClickListener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelection(position: Int) {
        selected = position
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_tanah, parent, false)
        return SingleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val (nama, deskripsi, urlPhoto) = datas[position]

        holder.mNamaTanah.text = nama
        holder.mDeskripsi.text = deskripsi
        Glide.with(holder.itemView.context)
            .load(urlPhoto)
            .into(holder.mPhoto)

        if (selected == position) {
            holder.itemView.isSelected = true
        } else {
            holder.itemView.isSelected = false
        }

        if (onClick != null) {
            holder.itemView.setOnClickListener {
                onClick!!.onItemClick(
                    holder.itemView,
                    holder.adapterPosition
                )
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class SingleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mNamaTanah: TextView = itemView.findViewById(R.id.tv_nama_tanah)
        var mDeskripsi: TextView = itemView.findViewById(R.id.tv_deskripsi)
        var mPhoto: ImageView = itemView.findViewById(R.id.avatar)
    }

}