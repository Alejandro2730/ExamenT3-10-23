package com.example.androidapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidapp.entity.Anime
import com.example.androidapp.services.AnimeService
import com.example.androidapp.services.AnimeService.ImageResponse
import com.example.androidapp.services.AnimeService.ImageToSave
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

class FormAnime : AppCompatActivity() {
    private var edtNombre: EditText? = null
    private val edtDescription: EditText? = null
    private val edtEmail: EditText? = null
    private var edtUrl: EditText? = null
    private var ivAvatar: ImageView? = null
    private var fotoEnBase64: String? = null
    private var photo: Bitmap? = null
    private var img: String? = null
    var btnCamara: Button? = null
    var btnGaleria: Button? = null
    private var btnSave: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        edtUrl = findViewById(R.id.edtNumberP)
        edtNombre = findViewById(R.id.edtNameP)
        //edtDescription = findViewById(R.id.edtTipoP);
        btnSave = findViewById(R.id.btnDelete)
        btnCamara = findViewById(R.id.btnCamara)
        btnGaleria = findViewById(R.id.btnGaleria)
        ivAvatar = findViewById(R.id.imageFoto)
        val animeList: List<Anime> = ArrayList()
        val anime = Anime()
        btnCamara.setOnClickListener(View.OnClickListener { handleOpenCamera() })
        btnGaleria.setOnClickListener(View.OnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, 2000)
            }
        })
        btnSave.setOnClickListener(View.OnClickListener {
            val url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/"
            anime.urlImagen = img
            anime.name = edtNombre.getText().toString()
            //anime.descripcion = edtDescription.getText().toString();
            val retrofit =
                Retrofit.Builder().baseUrl("https://64789771362560649a2e13af.mockapi.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val service = retrofit.create(AnimeService::class.java)
            service.create(anime).enqueue(object : Callback<Void?> {
                override fun onResponse(call: Call<Void?>, response: Response<Void?>) {
                    Toast.makeText(
                        this@FormAnime,
                        "Cambios guardados exitosamente",
                        Toast.LENGTH_SHORT
                    ).show() // Cerrar la actividad y regresar a la actividad anterior
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<Void?>, t: Throwable) {}
            })
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_CAMERA_REQUEST && resultCode == RESULT_OK) {
            photo = data!!.extras!!["data"] as Bitmap?
            ivAvatar!!.setImageBitmap(photo)
            val stream = ByteArrayOutputStream()
            photo!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
            val imgRetro = Retrofit.Builder()
                .baseUrl("https://demo-upn.bit2bittest.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val imageToSave = ImageToSave(fotoEnBase64)
            val imageService = imgRetro.create(AnimeService::class.java)
            val imgC = imageService.saveImage(imageToSave)
            imgC.enqueue(object : Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                    response: Response<ImageResponse>
                ) {
                    if (response.isSuccessful) {
                        println(response.body()!!.url)
                        img = "https://demo-upn.bit2bittest.com" + response.body()!!.url
                        edtUrl!!.setText(img)
                    } else Log.i("MAIN_APP", "No se subiÃ³")
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {}
            })
        }
        if (requestCode == OPEN_GALLERY_REQUEST && resultCode == RESULT_OK) {
            val selectedImage = data!!.data
            try {
                val inputStream = contentResolver.openInputStream(selectedImage!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                ivAvatar!!.setImageBitmap(bitmap)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()
                fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT)
                val imgRetro = Retrofit.Builder()
                    .baseUrl("https://demo-upn.bit2bittest.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val imageToSave = ImageToSave(fotoEnBase64)
                val imageService = imgRetro.create(AnimeService::class.java)
                val imgC = imageService.saveImage(imageToSave)
                imgC.enqueue(object : Callback<ImageResponse> {
                    override fun onResponse(
                        call: Call<ImageResponse>,
                        response: Response<ImageResponse>
                    ) {
                        if (response.isSuccessful) {
                            println(response.body()!!.url)
                            img = "https://demo-upn.bit2bittest.com" + response.body()!!.url
                        } else println("No subio")
                    }

                    override fun onFailure(call: Call<ImageResponse>, t: Throwable) {}
                })
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    private fun handleOpenCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // abrir camara
            Log.i("MAIN_APP", "Tiene permisos para abrir la camara")
            abrirCamara()
        } else {
            // solicitar el permiso
            Log.i("MAIN_APP", "No tiene permisos para abrir la camara, solicitando")
            val permissions = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissions, 1000)
        }
    }

    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, OPEN_CAMERA_REQUEST)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, OPEN_GALLERY_REQUEST)
    }

    companion object {
        private const val OPEN_CAMERA_REQUEST = 1001
        private const val OPEN_GALLERY_REQUEST = 1002
    }
}