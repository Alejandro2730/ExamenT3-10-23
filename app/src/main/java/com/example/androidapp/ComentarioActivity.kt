package com.example.androidapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ComentarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentario)
        /*
        EditText edtComentario = findViewById(R.id.edtComentario);
        Button btnSaveC = findViewById(R.id.btnSaveC);

        List<Comentario> cometarioList = new ArrayList<>();
        Comentario anime = new Comentario();

        btnSaveC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/";

                anime.coment = edtComentario.getText().toString();
                //anime.name = edtNombre.getText().toString();
                //anime.descripcion = edtDescription.getText().toString();




                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://647aba4dd2e5b6101db0815f.mockapi.io/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                AnimeService service = retrofit.create(AnimeService.class);
                service.create(anime).enqueue(new Callback<Void>(){

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(ComentarioActivity.this, "Cambios guardados exitosamente", Toast.LENGTH_SHORT).show();// Cerrar la actividad y regresar a la actividad anterior
                        Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
    */
    }
}