package com.example.bakerysraul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_historial_pedidos.*

class HistorialPedidos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pedidos)

        val exampleList = generateDummyList(size = 500)

        recycler_view.adapter = recyclerAdapter(exampleList)
        recycler_view.layoutManager= LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
    }

    private fun generateDummyList (size: Int): List<ItemExample> {
        val list = ArrayList<ItemExample>()

        for (i in 0 until size){
            val drawable = when (i % 3){
                0 -> R.drawable.item_icon1
                1 -> R.drawable.item_icon2
                else -> R.drawable.item_icon3
            }
            val item = ItemExample(drawable,"Item $i","Line 2")
            list+= item
        }
        return list
    }
}
