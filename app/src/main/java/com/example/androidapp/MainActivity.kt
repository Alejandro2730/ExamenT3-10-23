package com.example.androidapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val tvMessage: TextView? = null
    private val numbers: List<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_main)
        val btn1 = findViewById<Button>(R.id.btnMisPoke)
        val btn2 = findViewById<Button>(R.id.btnRegistrarPoke)
        btn1.setOnClickListener { view: View? ->
            val intent = Intent(applicationContext, ListaActivity::class.java)
            startActivity(intent)
        }
        btn2.setOnClickListener { view: View? ->
            val intent = Intent(applicationContext, FormAnime::class.java)
            startActivity(intent)
        }
    }
}