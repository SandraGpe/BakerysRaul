package com.example.bakerysraul

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_lineas_ped.*

class lineasPed : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lineas_ped)

        val pan = arrayListOf("1-Concha","2-Yoyo","3-Oreja","4-Bigote","5-Dona","6-Cuerno","7-Bolillo","8-Gragea","9-Quequis","10-Salado","11-Moño","12-Barquillo","13-Paloma","14-Rol","15-Campechana","16-Quesadilla")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,pan)


        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }
        }
    }
}
