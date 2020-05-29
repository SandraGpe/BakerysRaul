package com.example.bakerysraul

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.celda_prototipo_clientes.view.*

class ClientesAdapter (private var mListClientes:List<Clientes>,
private val mContext: Context, private val clickListener: (Clientes)-> Unit)
    : RecyclerView.Adapter<ClientesAdapter.ClientesViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientesAdapter.ClientesViewHolder {
        val  LayoutInflater= LayoutInflater.from(mContext).inflate(R.layout.celda_prototipo_clientes,parent,false)
        return ClientesAdapter.ClientesViewHolder (LayoutInflater)
    }

    override fun onBindViewHolder(holder: ClientesAdapter.ClientesViewHolder, position: Int) {
        holder.bind(mListClientes[position],mContext,clickListener)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int=mListClientes.size
    fun setTask(clientes: List<Clientes>){
        mListClientes = clientes
        notifyDataSetChanged()
    }

    fun getTask(): List<Clientes> = mListClientes
    class ClientesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(estud:Clientes,context: Context,clickListener: (Clientes) -> Unit) {
            itemView.Nombre.text = estud.nom.toString()
            itemView.Apellido.text = estud.ape.toString()
            itemView.setOnClickListener{clickListener(estud)}
            }
    }
}