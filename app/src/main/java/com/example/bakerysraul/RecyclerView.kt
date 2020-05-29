package com.example.bakerysraul

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerView : AppCompatActivity() {
    private lateinit var viewAdapter: ClientesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    val cList: List<Clientes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ClientesAdapter(cList,this,{estud:Clientes->onItemclickListener(estud)})
        //viewAdapter = ClientesAdapter(clientesList,this,{estud:Clientes -> onItemClickListener(estud)})

        rv_clientes_list.apply{
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(this@RecyclerView,
                DividerItemDecoration.VERTICAL))
        }
    }
}
