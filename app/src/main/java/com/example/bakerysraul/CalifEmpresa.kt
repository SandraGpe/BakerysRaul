package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calif_empresa.*

class CalifEmpresa : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calif_empresa)
    }
    fun graficaClic(v: View){
        val intGraf = Intent(this, CalifGrafica::class.java)
        startActivity(intGraf)
    }

    fun guardarClic(v: View){
        if (etNom.text.isEmpty() || etCalif.text.isEmpty()){
            etNom.setError("Falta información de ingresar")
            etNom.requestFocus()
        }
        else
        {
            val admin = adminbd(this)
            val nom = etNom.text.toString()
            val cal = etCalif.text.toString().toFloat()
            val sentencia = "Insert into EmpleadoCalif(NomEmp,Calif) values " +
                    "('${nom}',${cal})"
            if (admin.Ejecuta(sentencia)){
                Toast.makeText(this, "Empleado Guardado", Toast.LENGTH_SHORT).show();
                etCalif.setText("")
                etNom.setText("")
                etNom.requestFocus()
            }
            else
            {
                Toast.makeText(this, "Error de capa 8 No Guardo", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
