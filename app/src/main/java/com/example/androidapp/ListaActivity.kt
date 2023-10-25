package com.example.androidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.adapters.AnimeAdapter
import com.example.androidapp.entity.Anime
import com.example.androidapp.services.AnimeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaActivity : AppCompatActivity() {
    var rvListaSimple: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://64789771362560649a2e13af.mockapi.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(AnimeService::class.java)
        service.all.enqueue(object : Callback<List<Anime?>?> {
            override fun onResponse(call: Call<List<Anime?>?>, response: Response<List<Anime?>?>) {
                val items = response.body()
                rvListaSimple = findViewById(R.id.rvListaSimple)
                rvListaSimple?.layoutManager = LinearLayoutManager(applicationContext)
                rvListaSimple?.adapter = AnimeAdapter(items)
            }

            override fun onFailure(call: Call<List<Anime?>?>, t: Throwable) {
                // Manejar errores de la solicitud Retrofit aquí
            }
        })
    }
}
