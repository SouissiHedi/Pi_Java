package edu.esprit.entities;

import java.sql.Blob;
import javafx.scene.image.Image;

public class Jeu {
    private int id;
    private int jeu_id;
    private String nom;
    private String genre;
    private String developpeur;
    private Image image;
    private String url;
    private Blob game;

    public Jeu(int id, int jeu_id, String nom, String genre, String developpeur, Image image, Blob game) {
        this.id=id;
        this.jeu_id = jeu_id;
        this.nom = nom;
        this.genre = genre;
        this.developpeur = developpeur;
        this.image = image;
        this.game = game;
    }
    public Jeu(int id, int jeu_id, String nom, String genre, String developpeur, Image image,String url, Blob game) {
        this.id=id;
        this.jeu_id = jeu_id;
        this.nom = nom;
        this.genre = genre;
        this.developpeur = developpeur;
        this.url=url;
        this.image = image;
        this.game = game;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(String developpeur) {
        this.developpeur = developpeur;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Blob getGame() {
        return game;
    }

    public void setGame(Blob game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return "Jeu{" +
                "id=" + id +
                ", jeu_id=" + jeu_id +
                ", nom='" + nom + '\'' +
                ", genre='" + genre + '\'' +
                ", developpeur='" + developpeur + '\'' +
                '}';
    }
}
