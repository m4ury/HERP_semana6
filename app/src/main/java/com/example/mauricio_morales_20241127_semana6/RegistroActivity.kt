package com.example.mauricio_morales_20241127_semana6

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        val listViewRegistros = findViewById<ListView>(R.id.listViewRegistros)
        val dbHelper = DatabaseHelper(this)
        val registros = dbHelper.obtenerAsistencias()

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            registros.map { "${it["tipoMarca"]}, RUT: ${it["rut"]}, Fecha: ${it["fechaHora"]}" }
        )
        listViewRegistros.adapter = adapter
    }

}