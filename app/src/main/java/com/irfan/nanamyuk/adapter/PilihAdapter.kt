package com.irfan.nanamyuk.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.nanamyuk.R
import com.irfan.nanamyuk.data.api.PlantResponseItem

class PilihAdapter (private var datas: List<PlantResponseItem>) :
    RecyclerView.Adapter<PilihAdapter.SingleViewHolder>() {

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plant, parent, false)
        return SingleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        val (name, url, id) = datas[position]
        holder.mTvName.text = name
        Glide.with(holder.itemView).load(url).into(holder.image)
        holder.mTvId.text = id

        holder.itemView.isSelected = selected == position

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
        var mTvName: TextView = itemView.findViewById(R.id.tv_plant_name)
        var mTvId: TextView = itemView.findViewById(R.id.id)
        var image : ImageView = itemView.findViewById(R.id.circleImageView)
    }

}