package edu.esprit.entities;

import javafx.scene.image.Image;
import edu.esprit.entities.CategoryArticle;

public class Article {
    public int id;
    public int idA;
    public String nom;
    public String prix;
    public String description;
    public CategoryArticle type;
    public Image image;

    public Article() {
    }

    public Article(String nom, String prix,String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
    }

    public Article(int id, int idA, String nom, String prix, String description, CategoryArticle type, Image image) {
        this.id = id;
        this.idA = idA;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.type = type;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryArticle getType() {
        return type;
    }

    public void setType(CategoryArticle type) {
        this.type = type;
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
                ", nom='" + nom + '\'' +
                ", prix='" + prix + '\'' +
                '}';
    }
}
