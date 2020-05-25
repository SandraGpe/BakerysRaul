package com.example.bakerysraul

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_principal_menu.*


class PrincipalMenu : AppCompatActivity() {
    var corru : String = ""
    var contra : String = ""

    companion object{
        val EXTRA_ID = "id"
        val EXTRA_CONTRA = "contrasena"
    }
    val menu = arrayOf("Historial", "Carrito", "DetallePed", "Perfil", "Calificar", "Catalogo")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_menu)

        val reg = intent
        if (reg != null && reg.hasExtra(EXTRA_ID) && reg.hasExtra(EXTRA_CONTRA)){
            //se ejecuta cuando se haya logeado o cuando venga de un registro
            corru = reg.getStringExtra(EXTRA_ID)
            contra = reg.getStringExtra(EXTRA_CONTRA)
        } else {
            val acti : Intent = Intent(this,MainRegistro::class.java)
            startActivity(acti)
        }

        circle_menu.setMainMenu(Color.parseColor("#9e9e9e"), R.drawable.openopen, R.drawable.closeclose)
            .addSubMenu(Color.parseColor("#00b0ff"), R.drawable.historial)
            .addSubMenu(Color.parseColor("#76ff03"), R.drawable.carrito)
            .addSubMenu(Color.parseColor("#8e24aa"), R.drawable.detalle)
            .addSubMenu(Color.parseColor("#fb8c00"), R.drawable.user)
            .addSubMenu(Color.parseColor("#f50057"), R.drawable.calif)
            .addSubMenu(Color.parseColor("#ffeb3b"), R.drawable.catalogo)
            .setOnMenuSelectedListener{
                    index: Int -> Toast.makeText(this, "Selected "+ menu[index], Toast.LENGTH_SHORT).show()
                when(menu[index]){
                    "Historial"->{
                        val intent = Intent(this, HistorialPedidos::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                    "Carrito"->{
                        val intent = Intent(this, Compras::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                    "DetallePed"->{
                        val intent = Intent(this, DetallePedidos::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                    "Perfil"->{
                        val intent = Intent(this, UserPerfil::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                    "Calificar"->{
                        val intent = Intent(this, CalifEmpresa::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                    "Catalogo"->{
                        val intent = Intent(this, Compras::class.java)
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    }
                }
            }
}
}
