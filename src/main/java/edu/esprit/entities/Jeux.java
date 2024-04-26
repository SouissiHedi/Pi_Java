package edu.esprit.entities;

import javafx.scene.image.Image;

public class Jeux {
    public int id;
    public int jeu_id;
    public String nom;
    public String genre;
    public Image image;

    public Jeux() {
    }

    public Jeux(int id, int jeu_id,String nom, String genre) {
        this.id= id;
        this.jeu_id= jeu_id;
        this.nom = nom;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getJeu_id() {
        return jeu_id;
    }

    public void setJeu_id(int jeu_id) {
        this.jeu_id = jeu_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ",jeu_id='" + jeu_id + '\'' +
                ", nom='" + nom + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
