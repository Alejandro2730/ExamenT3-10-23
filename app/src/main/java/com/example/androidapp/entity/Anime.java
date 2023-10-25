package com.example.androidapp.entity;

import java.util.ArrayList;
import java.util.List;

public class Anime {
    public int id;
    public String urlImagen;

    public String name;
    public String descripcion;
    public String email;

    public List<Comentario> listaComentarios;
    public Anime() {
    }

    // Constructor con parámetros
    public Anime( String urlImagen, String name, String description, String email) {
        this.urlImagen = urlImagen;
        this.name = name;
        this.descripcion = description;
        this.email = email;
        this.listaComentarios = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public List<Comentario> getListaComentarios() {
        return listaComentarios;
    }

    public void setListaComentarios(List<Comentario> listaComentarios) {
        this.listaComentarios = listaComentarios;
    }

    public void agregarComentario(Comentario comentario) {
        listaComentarios.add(comentario);
    }
}
