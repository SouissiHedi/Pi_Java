package edu.esprit.entities;

public class Article {
    public int id;
    public String nom;
    public int prix;

    public Article() {
    }

    public Article(String nom, int prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }
}
