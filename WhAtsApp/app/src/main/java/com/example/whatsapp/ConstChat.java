package com.example.whatsapp;


import java.io.Serializable;

public class ConstChat implements Serializable {

    private String nom;
    private String ape;
    private int foto;

    public ConstChat(String s) {
    }

    public ConstChat(String nom, String ape, int foto) {
        this.nom = nom;
        this.ape = ape;
        this.foto = foto;
    }

    public String getNom() {
        return nom;
    }

    public String getApe() {
        return ape;
    }

    public int getFoto() {
        return foto;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setApe(String ape) {
        this.ape = ape;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
}
