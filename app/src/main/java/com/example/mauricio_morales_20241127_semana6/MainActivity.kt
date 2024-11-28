package com.example.mauricio_morales_20241127_semana6

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val dbHelper = DatabaseHelper(this)

        val editTextRut = findViewById<EditText>(R.id.editTextRut)
        val editTextNombre = findViewById<EditText>(R.id.editTextNombre)
        val editTextApellido = findViewById<EditText>(R.id.editTextApellido)
        val radioGroupTipoMarca = findViewById<RadioGroup>(R.id.radioGroupTipoMarca)
        val buttonRegistrar = findViewById<Button>(R.id.buttonRegistrar)
        val buttonVerRegistros = findViewById<Button>(R.id.buttonVerRegistros)

        buttonRegistrar.setOnClickListener {
            val rut = editTextRut.text.toString()
            val nombre = editTextNombre.text.toString()
            val apellido = editTextApellido.text.toString()
            val fechaHora = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(java.util.Date())
            val tipoMarca = if (radioGroupTipoMarca.checkedRadioButtonId == R.id.radioEntrada) "Entrada" else "Salida"

            if (rut.isNotBlank() && nombre.isNotBlank() && apellido.isNotBlank()) {
                val resultado = dbHelper.insertarAsistencia(rut, nombre, apellido, fechaHora, tipoMarca)
                if (resultado != -1L) {
                    Toast.makeText(this, "Asistencia registrada con éxito", Toast.LENGTH_SHORT).show()
                    editTextRut.text.clear()
                    editTextNombre.text.clear()
                    editTextApellido.text.clear()
                } else {
                    Toast.makeText(this, "Error al registrar asistencia", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        buttonVerRegistros.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }
}