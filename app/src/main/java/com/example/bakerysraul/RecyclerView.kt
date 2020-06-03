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
    private lateinit var viewAdapter:ClientesAdapter
    private lateinit var viewManager:RecyclerView.LayoutManager
    val personasList: List<Clientes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ClientesAdapter(personasList,this, clickListener = { estud:Clientes -> onItemClickListener(estud)})

                recycler.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@RecyclerView,
                DividerItemDecoration.VERTICAL))
        }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder)
                    : Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               val position = viewHolder.adapterPosition
                val estud = viewAdapter.getTask()
                val admin = adminbd(baseContext)
                if(admin.Ejecuta("DELETE from perfiles where idCliente=" + estud[position].id)==1){
                    retrievePerfiles()
                }
            }
        }).attachToRecyclerView(recycler)
    }

    private fun onItemClickListener(estud:Clientes){
        Toast.makeText(this,"Clicked item" + estud.nom, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        retrievePerfiles()
    }

    private fun retrievePerfiles(){
        val perfiltx = getClientes()
        viewAdapter.setTask(perfiltx!!)
    }

    fun getClientes(): MutableList<Clientes>{
        var perfiles:MutableList<Clientes> = ArrayList()
        val admin = adminbd(this)

        //
        val tupla = admin.Consulta("SELECT idCliente, nomCliente, apeCliente FROM perfiles ORDER BY idCliente")
        while (tupla!!.moveToNext()){
            val id = tupla.getString(0)
            val nom = tupla.getString(1)
            val ape = tupla.getString(2)
            perfiles.add(Clientes(id,nom,ape))
        }
        tupla.close()
        admin.close()
        return perfiles
    }
}
