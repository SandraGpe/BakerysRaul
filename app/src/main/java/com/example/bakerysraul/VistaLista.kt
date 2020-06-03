package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_vista_lista.*
import kotlinx.android.synthetic.main.celda_prototipo_clientes.*
import kotlinx.android.synthetic.main.celda_prototipo_clientes.txtApe

class VistaLista : AppCompatActivity() {
    val IP = "http://192.168.1.77"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_lista)
    }
    fun save(v: View){
        if (idNom.text.isEmpty() || idApe.text.isEmpty()){
            idNom.setError("Falta información de ingresar")
            idNom.requestFocus()
        }
        else
        {
            val admin = adminbd(this)
            val nom = idNom.text.toString()
            val ape = idApe.text.toString()
            val sentencia = "Insert into perfiles(nomCliente,apeCliente) values ('${nom}','${ape}')"
            if (admin.Ejecuta(sentencia)==1){
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
                idNom.setText("")
                idApe.setText("")
                idNom.requestFocus()
            } else {
                Toast.makeText(this, "Error de capa 8 No Guardo", Toast.LENGTH_SHORT).show(); }
        }
    }

    fun verlista(v:View){
        val lista = Intent(this, RecyclerView::class.java)
        startActivity(lista)
    }
}
