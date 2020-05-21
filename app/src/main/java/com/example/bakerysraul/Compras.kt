package com.example.bakerysraul

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_compras.*

class Compras : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compras)

        concha.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        orejas.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        beso.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        bigote.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        bolillo.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        cuernos.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        donas.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        gragea.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        mono.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        quequis.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        salado.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        barquillo.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        paloma.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        roles.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        campechana.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }
        quesadilla.setOnClickListener{
            val intent: Intent = Intent(this,DetallePedidos::class.java)
            startActivity(intent)
        }

    }
}
