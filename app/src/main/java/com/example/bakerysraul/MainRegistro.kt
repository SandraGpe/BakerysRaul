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
import kotlinx.android.synthetic.main.activity_main_registro.*
import kotlinx.android.synthetic.main.activity_main_registro.txtContra
import kotlinx.android.synthetic.main.activity_main_registro.txtCorr
import org.json.JSONObject

class MainRegistro : AppCompatActivity() {
    val IP = "http://192.168.1.77" // Dirección IP del servidor web que almacena los servicios web

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_registro)
    }

    fun btnReg(v: View){

    if (txtCorr.text.isEmpty() ||
        txtNom.text.isEmpty() ||
        txtContra.text.isEmpty()) {
        txtCorr.setError("Error faltan datos por ingresar")
        txtCorr.requestFocus()
    } else {
        val corr = txtCorr.text.toString()
        val nom = txtNom.text.toString()
        val contra = txtContra.text.toString()
        var jsonEntrada = JSONObject()
        jsonEntrada.put("nomusr", txtNom.text.toString())
        jsonEntrada.put("correoUsr", txtCorr.text.toString())
        jsonEntrada.put("contrasena",txtContra.text.toString())
        sendRequest(IP + "/WSBakery/bdInsert.php",jsonEntrada)
            txtCorr.setText("")
            txtNom.setText("")
            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show();
            val acti : Intent= Intent(this,PrincipalMenu::class.java)
            acti.putExtra(PrincipalMenu.EXTRA_CORR,corr)
            acti.putExtra(PrincipalMenu.EXTRA_CONTRA,contra)
            startActivity(acti)
         }
        }


    /*fun Agregar(view: View) {
        if (txtNom.text.toString().isEmpty()||
            txtCorr.text.toString().isEmpty()||
            txtContra.text.toString().isEmpty()){
            txtCorr.setError("Falta información de Ingresar")
            txtCorr.requestFocus()
        }
        else
        {
            val nom = txtNom.text.toString()
            val corr = txtCorr.text.toString()
            val contra = txtContra.text.toString()
            var jsonEntrada = JSONObject()
            jsonEntrada.put("nomProd", txtNom.text.toString())
            jsonEntrada.put("existencia", txtCorr.text.toString())
            jsonEntrada.put("precio",txtCorr.text.toString())
            sendRequest(IP + "/WSBakery/bdInsert.php",jsonEntrada)
        }
    }*/
    //Rutina para mandar ejecutar un web service de tipo Insert, Update o Delete
    fun sendRequest( wsURL: String, jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200){
                    txtCorr.setText("")
                    txtNom.setText("")
                    txtContra.setText("")
                    txtCorr.requestFocus()
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
