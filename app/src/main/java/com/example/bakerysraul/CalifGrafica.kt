package com.example.bakerysraul

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_calif_grafica.*

class CalifGrafica : AppCompatActivity() {

    val entries = ArrayList<BarEntry>() // Arreglo para cargar las ventas de los empleados eje de las Y
    val labels = ArrayList<String>() // Arreglo para cargar los nombres de empleados en el eje de las X
    var cursor : Cursor?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calif_grafica)
        cargarDatos()
        setBarChart()
    }
    fun cargarDatos(){
        val admin = adminbd(this)
        cursor = admin.Consulta("Select * from EmpleadoCalif Order by Calif")
    }

    fun setBarChart() {
        var i = 0
        if (cursor!!.moveToFirst()){
            do {
                val nom = cursor!!.getString(1)
                val cal = cursor!!.getFloat(2)
                entries.add(BarEntry(cal, i)) // Agregamos Ventas
                labels.add(nom)                // Agregamos nomEmp
                i++
            } while (cursor!!.moveToNext())
            val barDataSet = BarDataSet(entries, "Datos")
            val data = BarData(labels, barDataSet)
            barChart.data = data
            barChart.setDescription("Graficas Calificacion X Empleado")
            barDataSet.color = resources.getColor(R.color.colorAccent)
            barChart.animateY(5000)
        }
    }
}
