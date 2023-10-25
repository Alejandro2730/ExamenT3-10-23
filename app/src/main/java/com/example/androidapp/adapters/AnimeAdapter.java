package com.example.androidapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.ComentarioActivity;
import com.example.androidapp.FuncionActivity;
import com.example.androidapp.ListaActivity;
import com.example.androidapp.entity.Anime;
import com.example.androidapp.R;
import com.example.androidapp.entity.Comentario;
import com.example.androidapp.services.AnimeService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeAdapter extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder> {

    List<Anime> items;

    public AnimeAdapter(List<Anime> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_string, parent, false);
        AnimeViewHolder viewHolder = new AnimeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        
        
        Anime item = items.get(position);
        View view = holder.itemView;

        int id = item.getId();
        //TextView tvEmail = view.findViewById(R.id.tvNumber);
        //tvEmail.setText(item.urlImagen);

        TextView tvName = view.findViewById(R.id.tvName);
        tvName.setText(item.name);

        //TextView tvDescripcion = view.findViewById(R.id.tvTipo);
        //tvDescripcion.setText(item.descripcion);

        ImageView imgFoto = view.findViewById(R.id.imgFoto);
        Picasso.get().load(item.urlImagen).into(imgFoto);

        Button btnEditar = view.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ComentarioActivity.class);
                intent.putExtra("id", item.getId());
                intent.putExtra("url", item.getUrlImagen());
                intent.putExtra("name", item.getName());
                intent.putExtra("descripcion", item.getDescripcion());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class AnimeViewHolder extends RecyclerView.ViewHolder {

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
