package com.example.bakerysraul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerView : AppCompatActivity() {
    private lateinit var viewAdapter: ClientesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    val cList: List<Clientes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ClientesAdapter(cList,this, clickListener = { estud:Clientes -> onItemClickListener(estud)})

                rv_clientes_list.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@RecyclerView,
                DividerItemDecoration.VERTICAL))
        }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                val estud = viewAdapter.getTask()
                val admin = adminbd(baseContext)
                if(admin.Ejecuta("DELETE from perfil where idCliente=" + estud[position].id)==1){
                    retrieveClientes()
                }
            }
        }).attachToRecyclerView(rv_clientes_list)
    }

    private fun onItemClickListener(Estud:Clientes){
        Toast.makeText(this,"Clicked item" + Estud.nom, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        retrieveClientes()
    }

    private fun retrieveClientes(){
        val clientestx = getClientes()
        viewAdapter.setTask(clientestx!!)
    }

    fun getClientes(): MutableList<Clientes>{
        var perfil:MutableList<Clientes> = ArrayList()
        val admin = adminbd(this)

        //
        val tupla = admin.Consulta("SELECT idCliente, nomCliente, apellidoCliente FROM perfil ORDER BY idCliente")
        while (tupla!!.moveToNext()){
            val id = tupla.getString(0)
            val nom = tupla.getString(1)
            val ape = tupla.getString(2)
            perfil.add(Clientes(id,nom,ape))
        }
        tupla.close()
        admin.close()
        return perfil
    }
}
