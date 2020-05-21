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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_perfil.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val IP = "http://192.168.1.77" // Dirección IP del servidor web que almacena los servicios web
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun empleados(v:View){
        val wsURL = IP + "/WSBakery/bdLogin.php"
        val admin = adminbd(this)
        admin.Ejecuta("DELETE FROM usuario")
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,wsURL,null,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                val sensadoJson = response.getJSONArray("empleados")
                for (i in 0 until sensadoJson.length()){
                    // Los nombres del getString son como los arroja el servicio web
                    val corr = sensadoJson.getJSONObject(i).getString("correoUsr")
                    val contra = sensadoJson.getJSONObject(i).getString("contrasena")
                    val sentencia = "INSERT INTO usuario(corrUsr,nomusr,contrasena) values(${corr}, '${null}','${contra})"
                    val res = admin.Ejecuta(sentencia)
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error Empleados: " + error.message.toString() , Toast.LENGTH_LONG).show();
                Log.d("GonzalezSandra",error.message.toString() )
            }
        )
        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun btnLogin(view: View){
        if (txtCorr.text.isEmpty() || txtContra.text.isEmpty()){
            txtCorr.setError("Error, faltan datos de entrada")
            txtCorr.requestFocus()
        } else{
            var corr = txtCorr.text.toString()
            var contra = txtContra.text.toString()
            var admin = adminbd(this)
            var query = "Select * from usuarios where correoUsr = '$corr' and contrasena='$contra'"
            val resul = admin.Consulta(query)
            if (resul!!.moveToFirst()){
                corr = resul.getString(0)
                contra = resul.getString(2)
                val acti = Intent(this, PrincipalMenu::class.java)
                acti.putExtra(PrincipalMenu.EXTRA_CORR,corr)
                acti.putExtra(PrincipalMenu.EXTRA_CONTRA,contra)
                startActivity(acti)
            } else{
                Toast.makeText(this, "Correo o contraseña invalido", Toast.LENGTH_LONG).show();
                txtCorr.requestFocus()
            }
        }
    }

    fun btnRegistrar(v: View){
        val acti : Intent = Intent(this,MainRegistro::class.java)
        startActivity(acti)
    }

   
}
