package com.irfan.nanamyuk.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.irfan.nanamyuk.R

class PilihAdapter (context: Context, private var datas: List<String>) :
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
        val name = datas[position]
        holder.mTvName.text = name
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
        var mTvName: TextView = itemView.findViewById(R.id.tv_plant_name)
    }

}