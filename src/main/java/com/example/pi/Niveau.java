package com.example.pi;

public class Niveau {
    private int id;
    private String numero;
    private String division;
    private String image; // Nouvel attribut pour l'image

    // Constructeur
    public Niveau(int id, String numero, String division, String image) {
        this.id = id;
        this.numero = numero;
        this.division = division;
        this.image = image;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
