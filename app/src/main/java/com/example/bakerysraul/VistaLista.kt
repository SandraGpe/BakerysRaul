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

class VistaLista : AppCompatActivity() {
    val IP = "http://192.168.1.77"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_lista)
    }
    fun getAllClientes(view: View) {
        val wsURL = IP + "/WSBakery/getClientes.php"
        val admin = adminbd(this)
        admin.Ejecuta("DELETE FROM perfil")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,null,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val sensadoJson = response.getJSONArray("clientes")
                for (i in 0 until sensadoJson.length()){
                    // Los nombres del getString son como los arroja el servicio web
                    val idcliente = sensadoJson.getJSONObject(i).getString("idCliente")
                    val nomcliente = sensadoJson.getJSONObject(i).getString("nomCliente")
                    val apellidoCliente = sensadoJson.getJSONObject(i).getString("apellidoCliente")
                    val rfc = sensadoJson.getJSONObject(i).getString("RFC")
                    val comunidad = sensadoJson.getJSONObject(i).getString("comunidad")
                    val colonia= sensadoJson.getJSONObject(i).getString("colonia")
                    val calle = sensadoJson.getJSONObject(i).getString("calle")
                    val cp= sensadoJson.getJSONObject(i).getString("cp")
                    val tel = sensadoJson.getJSONObject(i).getString("tel")
                    val sentencia = "Insert into perfil(idCliente,nomCliente,apellidoCliente,RFC,comunidad,colonia,calle,cp,tel) values(${idcliente},'${nomcliente}','${apellidoCliente}','${rfc}','${comunidad}','${colonia}','${calle}',${cp},${tel})"
                    val res = admin.Ejecuta(sentencia)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error getAllClientes: " + error.message.toString() , Toast.LENGTH_LONG).show();
                Log.d("GonzalezSandra",error.message.toString() )
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun verlista(v:View){
        val lista = Intent(this, RecyclerView::class.java)
        startActivity(lista)
    }
}
