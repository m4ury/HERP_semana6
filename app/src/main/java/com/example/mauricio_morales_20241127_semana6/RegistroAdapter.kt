package com.example.mauricio_morales_20241127_semana6

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class RegistroAdapter(
    private val context: Context,
    private val registros: List<Map<String, String>>
) : BaseAdapter() {

    override fun getCount(): Int = registros.size
    override fun getItem(position: Int): Any = registros[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_registro, parent, false)

        val titulo = view.findViewById<TextView>(R.id.textViewTitulo)
        val detalle = view.findViewById<TextView>(R.id.textViewDetalle)
        val buttonCompartir = view.findViewById<Button>(R.id.buttonCompartir)

        val registro = registros[position]
        val tipoMarca = registro["tipoMarca"] ?: ""
        val detalleTexto = """
            RUT: ${registro["rut"]}
            Fecha y Hora: ${registro["fechaHora"]}
        """.trimIndent()

        titulo.text = if (tipoMarca == "Entrada") "ENTRADA" else "SALIDA"
        detalle.text = detalleTexto

        detalle.setTextColor(if (tipoMarca == "Entrada") Color.GREEN else Color.RED)

        buttonCompartir.setOnClickListener {
            val textoCompartir = """
                Detalles del registro:
                RUT: ${registro["rut"]}
                Fecha y Hora: ${registro["fechaHora"]}
                Tipo: $tipoMarca
            """.trimIndent()

            val intentCompartir = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textoCompartir)
            }

            try {
                context.startActivity(Intent.createChooser(intentCompartir, "Compartir registro"))
            } catch (e: Exception) {
                Toast.makeText(context, "No se pudo compartir el registro", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}