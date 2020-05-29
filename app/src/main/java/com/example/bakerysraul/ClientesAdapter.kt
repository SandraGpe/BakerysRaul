package com.example.bakerysraul

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ClientesAdapter (private var mListClientes:List<Clientes>,
private val mContext: Context, private val clickListener: (Clientes)-> Unit)
    : RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesAdapter.ClientesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.,parent,false)
        return recyclerAdapter.recyclerViewHolder(itemView)
    }
}