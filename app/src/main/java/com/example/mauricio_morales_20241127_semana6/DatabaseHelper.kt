package com.example.mauricio_morales_20241127_semana6

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "control_asistencia.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "asistencias"
        const val COLUMN_ID = "id"
        const val COLUMN_RUT = "rut"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_FECHA_HORA = "fecha_hora"
        const val COLUMN_TIPO_MARCA = "tipo_marca"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RUT TEXT NOT NULL,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_APELLIDO TEXT NOT NULL,
                $COLUMN_FECHA_HORA TEXT NOT NULL,
                $COLUMN_TIPO_MARCA TEXT NOT NULL
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertarAsistencia(rut: String, nombre: String, apellido: String, fechaHora: String, tipoMarca: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RUT, rut)
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_FECHA_HORA, fechaHora)
            put(COLUMN_TIPO_MARCA, tipoMarca)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun obtenerAsistencias(): List<Map<String, String>> {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, "$COLUMN_FECHA_HORA DESC")
        val registros = mutableListOf<Map<String, String>>()

        with(cursor) {
            while (moveToNext()) {
                val registro = mapOf(
                    "rut" to getString(getColumnIndexOrThrow(COLUMN_RUT)),
                    "nombre" to getString(getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    "apellido" to getString(getColumnIndexOrThrow(COLUMN_APELLIDO)),
                    "fechaHora" to getString(getColumnIndexOrThrow(COLUMN_FECHA_HORA)),
                    "tipoMarca" to getString(getColumnIndexOrThrow(COLUMN_TIPO_MARCA))
                )
                registros.add(registro)
            }
            close()
        }
        return registros
    }
}
