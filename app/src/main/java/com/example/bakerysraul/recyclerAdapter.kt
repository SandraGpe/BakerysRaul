package com.example.bakerysraul

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_items.view.*

class recyclerAdapter(private val exampleList: List<ItemExample>):RecyclerView.Adapter<recyclerAdapter.recyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_items,parent,false)
        return recyclerViewHolder(itemView)
    }

    override fun getItemCount() = exampleList.size

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.imageView.setImageResource(currentItem.imageResource)
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2

        if (position == 0){
            holder.textView1.setBackgroundColor(Color.CYAN)
        }else{
            holder.textView1.setBackgroundColor(Color.YELLOW)
        }

    }

    class recyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.item1
        val textView1: TextView = itemView.txt_item1
        val textView2: TextView = itemView.txt2_item1

    }
}