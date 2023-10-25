package com.example.androidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp.entity.Anime
import com.example.androidapp.services.AnimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FuncionActivity : AppCompatActivity() {
    private var edtUrl: EditText? = null
    private var edtName: EditText? = null
    private var edtDescription: EditText? = null
    private val edtEmail: EditText? = null
    private val btnDelete: Button? = null
    private var btnActualizar: Button? = null
    private val btnAtras: Button? = null
    private var animeService: AnimeService? = null
    private var currentItem: Anime? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcion)
        edtUrl = findViewById(R.id.edtUrlImagen)
        edtName = findViewById(R.id.edtName)
        edtDescription = findViewById(R.id.edtDescripcion)
        //btnDelete = findViewById(R.id.btnDelete);
        btnActualizar = findViewById(R.id.btnActualizar)
        val id = intent.getIntExtra("id", 0)
        val urlImagen = intent.getStringExtra("url")
        val name = intent.getStringExtra("name")
        val descripcion = intent.getStringExtra("descripcion")
        if (urlImagen != null && name != null && descripcion != null) {
            currentItem = Anime()
            currentItem!!.setId(id)
            currentItem!!.setUrlImagen(urlImagen)
            currentItem!!.setName(name)
            currentItem!!.setDescripcion(descripcion)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl("https://64789771362560649a2e13af.mockapi.io/") // Reemplaza con tu URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        animeService = retrofit.create(AnimeService::class.java)

        // Mostrar los datos del elemento seleccionado en los campos de edición
        if (currentItem != null) {
            edtUrl.setText(currentItem!!.getUrlImagen())
            edtName.setText(currentItem!!.getName())
            edtDescription.setText(currentItem!!.getDescripcion())
        }

        // Configurar el botón de guardado
        btnActualizar.setOnClickListener(View.OnClickListener { deleteAnime(currentItem!!.getId()) })
        // btnDelete.setOnClickListener(new View.OnClickListener() {
        //   @Override
        // public void onClick(View v) {
        //           deleteAnime(currentItem.getId());
        //}
        //});
    }

    private fun saveChanges() {
        // Obtener los nuevos valores ingresados por el usuario
        val newUrl = edtUrl!!.text.toString()
        val newName = edtName!!.text.toString()
        val newDescription = edtDescription!!.text.toString()

        // Actualizar los datos del elemento seleccionado
        if (currentItem != null) {
            currentItem!!.setUrlImagen(newUrl)
            currentItem!!.setName(newName)
            currentItem!!.setDescripcion(newDescription)

            // Llamar a la API para actualizar el elemento
            val call = animeService!!.update(currentItem!!.getId(), currentItem)
            call.enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@FuncionActivity,
                            "Cambios guardados exitosamente",
                            Toast.LENGTH_SHORT
                        ).show() // Cerrar la actividad y regresar a la actividad anterior
                    } else {
                        Toast.makeText(
                            this@FuncionActivity,
                            "Error al guardar los cambios",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {
                    Toast.makeText(this@FuncionActivity, "Error de conexión", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    private fun deleteAnime(id: Int) {
        val call = animeService!!.delete(id)
        call.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@FuncionActivity,
                        "Anime eliminado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show() // Cerrar la actividad y regresar a la actividad anterior
                    val intent = Intent(applicationContext, ListaActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@FuncionActivity,
                        "Error al eliminar el anime",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Toast.makeText(this@FuncionActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}