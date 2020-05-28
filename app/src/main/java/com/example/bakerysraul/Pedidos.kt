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
import kotlinx.android.synthetic.main.activity_pedidos.*
import org.json.JSONObject

class Pedidos : AppCompatActivity() {
    val IP = "http://192.168.0.7"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)

        /*val dia = arrayListOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")
        val mes = arrayListOf("Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,mes+dia)
        val arrayAdapter1 = ArrayAdapter(this,android.R.layout.simple_list_item_1,dia)


        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                this@Pedidos.tvMes.text
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                tvMes.text = mes[position]
            }
        }
        spinner2.adapter = arrayAdapter1
        spinner2.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Dia.text="Elige una opcion"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Dia.text = dia[position]
            }
        }*/
    }
    fun Guardar(v: View){
        if (txtIDCli.text.toString().isEmpty() ||
            txtIDPed.text.toString().isEmpty() ||
            Mes.text.toString().isEmpty() ||
            Dia.text.toString().isEmpty()) {
            txtIDPed.setError("Falta información de Ingresar")
            txtIDPed.requestFocus()
        } else {
            val ped = txtIDPed.text.toString()
            val cli = txtIDCli.text.toString()
            val m = Mes.text.toString()
            val d = Dia.text.toString()
            val admin = adminbd(this)
            val sentencia = "Insert into pedidos(idPed,idCli,mes,dia) values " +
                    "(${ped},${cli},'${m}',${d})"
            var jsonEntrada = JSONObject()
            jsonEntrada.put("idPed", txtIDPed.text.toString())
            jsonEntrada.put("idCli", txtIDCli.text.toString())
            jsonEntrada.put("Mes",Mes.text.toString())
            jsonEntrada.put("Dia",Dia.text.toString())
            sendRequest(IP + "/WSBakery/agregarPedido.php",jsonEntrada)
            if (admin.Ejecuta(sentencia)==1){
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
                txtIDPed.setText("")
                txtIDCli.setText("")
                Mes.setText("")
                Dia.setText("")
                txtIDPed.requestFocus()
            }
            else
            {
                Toast.makeText(this, "Error de capa 8 No Guardo", Toast.LENGTH_SHORT).show();
            }


        }
    }

    fun aaa(v:View){
        val intent: Intent = Intent(this,lineasPed::class.java)
        startActivity(intent)
    }

    fun sendRequest( wsURL: String, jsonEnt: JSONObject){
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, wsURL,jsonEnt,
            Response.Listener { response ->
                val succ = response["success"]
                val msg = response["message"]
                if (succ == 200){
                    txtIDPed.setText("")
                    txtIDCli.setText("")
                    Mes.setText("")
                    Dia.setText("")
                    txtIDPed.requestFocus()
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
