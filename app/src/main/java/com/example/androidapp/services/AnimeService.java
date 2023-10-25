package com.example.androidapp.services;
import com.example.androidapp.entity.Anime;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import com.google.gson.annotations.SerializedName;

public interface AnimeService {
    @GET("anime")
    Call<List<Anime>> getAll();
    @POST("anime")
    Call<Void> create(@Body Anime anime);

    @GET("anime/{id}")
    Call<Anime> findAnime(@Path("id") int id);

    @PUT("anime/{id}")
    Call<Void> update(@Path("id") int id, @Body Anime anime);

    @DELETE("anime/{id}")
    Call<Void> delete(@Path("id") int id);

    @POST("image")
    Call<ImageResponse> saveImage(@Body ImageToSave image);


    class ImageResponse {
        @SerializedName("url")
        private String url;

        public String getUrl(){
            return url;
        }
    }
    class ImageToSave {
        String base64Image;

        public ImageToSave(String base64Image){
            this.base64Image = base64Image;
        }
    }
}

