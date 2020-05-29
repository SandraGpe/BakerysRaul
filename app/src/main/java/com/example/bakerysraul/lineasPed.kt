package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_lineas_ped.*
import org.json.JSONObject

class lineasPed : AppCompatActivity() {
    val IP = "http://192.168.1.77"
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

    fun Agregar(v: View){
        if (idPedido.text.toString().isEmpty() ||
            lineas.text.toString().isEmpty() ||
            idPan.text.toString().isEmpty() ||
            cantidad.text.toString().isEmpty() ||
            preciounitario.text.toString().isEmpty()){
            idPedido.setError("Falta información de Ingresar")
            idPedido.requestFocus()
        } else {
            val id = idPedido.text.toString()
            val idc = idCliente.text.toString()
            val num = lineas.text.toString()
            val idp = idPan.text.toString()
            val cant = cantidad.text.toString()
            val pre = preciounitario.text.toString()
            val admin = adminbd(this)
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idPedido", idPedido.text.toString())
            jsonEntrada.put("idCliente", idCliente.text.toString())
            jsonEntrada.put("lineas", lineas.text.toString())
            jsonEntrada.put("idPan",idPan.text.toString())
            jsonEntrada.put("cantidad",cantidad.text.toString())
            jsonEntrada.put("precioUnit",preciounitario.text.toString())
            sendRequest(IP + "/WSBakery/agregarPanesPed.php",jsonEntrada)
        }
    }
    fun regresar(v:View){
        val intent: Intent = Intent(this,PrincipalMenu::class.java)
        startActivity(intent)
    }
    fun limpiar(v:View){
        idPan.setText("")
        cantidad.setText("")
        preciounitario.setText("")

    }
    fun sendRequest( wsURL: String, jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200){
                    idPedido.setText("")
                    lineas.setText("")
                    idPan.setText("")
                    cantidad.setText("")
                    preciounitario.setText("")
                    idCliente.setText("")
                    idPedido.requestFocus()
                    Toast.makeText(this, "Success:${succ}  Message:${msg} Servidor Web Modificado", Toast.LENGTH_SHORT).show();
                }
            },
            Response.ErrorListener{ error ->
                Toast.makeText(this, "${error.message}", Toast.LENGTH_SHORT).show();
                Log.d("ERROR","${error.message}");
                Toast.makeText(this, "Error de capa 8 checa URL", Toast.LENGTH_SHORT).show();
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}
