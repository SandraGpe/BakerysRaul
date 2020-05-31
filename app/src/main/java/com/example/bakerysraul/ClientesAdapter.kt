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
    /**
     * La clase RecyclerView. onBindViewHolder() se encarga de coger cada una de las
     * posiciones de la lista de estudiantes y pasarlas a la clase ViewHolder(
     *
     * @param holder   Vincular los datos del cursor al ViewHolder
     * @param position La posición de los datos en la lista
     */

    override fun onBindViewHolder(holder: ClientesAdapter.ClientesViewHolder, position: Int) {
        holder.bind(mListClientes[position],mContext,clickListener)
    }

    /**
     * El método getItemCount() nos devuelve el tamaño de la lista, que lo necesita
     * el RecyclerView.
     */

    override fun getItemCount(): Int=mListClientes.size
    /**
     * Cuando los datos cambian, este metodo actualiza la lista de estudiantes
     * y notifica al adaptador a usar estos nuevos valores
     */

    fun setTask(clientes: List<Clientes>){
        mListClientes = clientes
        notifyDataSetChanged()
    }
    /**
     * Clase interna para crear ViewHolders
     */

    fun getTask(): List<Clientes> = mListClientes
    class ClientesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(estud:Clientes,context: Context,clickListener: (Clientes) -> Unit) {
            itemView.idC.text = estud.id.toString()
            itemView.Nombre.text = estud.nom.toString()
            itemView.Apellido.text = estud.ape.toString()
            itemView.setOnClickListener{clickListener(estud)}
            }
    }
}