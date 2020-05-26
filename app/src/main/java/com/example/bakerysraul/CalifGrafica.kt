package com.example.bakerysraul

import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.activity_calif_grafica.*

class CalifGrafica : AppCompatActivity() {
    /*var idE : String = ""
    var cal : String = ""
    companion object{
        val EXTRA_idE = "idE"
        val EXTRA_Calif = "calif"
    }*/

    val entries = ArrayList<BarEntry>() // Arreglo para cargar las calif de los empleados eje de las Y
    val labels = ArrayList<String>() // Arreglo para cargar los ID de empleados en el eje de las X
    var cursor : Cursor?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calif_grafica)
        cargarDatos()
        setBarChart()

        /*val reg = intent
        if (reg != null && reg.hasExtra(EXTRA_idE) && reg.hasExtra(EXTRA_Calif)){
            //se ejecuta cuando se haya logeado o cuando venga de un registro
            idE = reg.getStringExtra(EXTRA_idE)
            cal = reg.getStringExtra(EXTRA_Calif)
        } else {
            val acti : Intent = Intent(this,CalifEmpresa::class.java)
            startActivity(acti)
        }*/
    }
    fun cargarDatos(){
        val admin = adminbd(this)
        cursor = admin.Consulta("Select * from calificar Order by calif")
    }

    fun setBarChart() {
        var i = 0
        if (cursor!!.moveToFirst()){
            do {
                val emp = cursor!!.getString(1)
                val cal = cursor!!.getFloat(2)
                entries.add(BarEntry(cal, i)) // Agregamos Ventas
                labels.add(emp)                // Agregamos nomEmp
                i++
            } while (cursor!!.moveToNext())
            val barDataSet = BarDataSet(entries, "Datos")
            val data = BarData(labels, barDataSet)
            barChart.data = data
            barChart.setDescription("Graficas Ventas X Empleado")
            barDataSet.color = resources.getColor(R.color.colorAccent)
            barChart.animateY(5000)
        }
    }
}
