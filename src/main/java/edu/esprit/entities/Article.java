package edu.esprit.entities;

import javafx.scene.image.Image;
import edu.esprit.entities.CategoryArticle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Article {
    public int id;
    public int idA;
    public String nom;
    public String prix;
    public String description;
    public CategoryArticle type;
    public Image image;
    public String url;
    private PropertyChangeSupport support;


    public Article() {
        this.support = new PropertyChangeSupport(this);
    }

    public Article(String nom, String prix,String description) {
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.support = new PropertyChangeSupport(this);
    }

    public Article(int id, int idA, String nom, String prix, String description, CategoryArticle type, Image image) {
        this.id = id;
        this.idA = idA;
        this.nom = nom;
        this.prix = prix;
        this.description = description;
        this.type = type;
        this.image = image;
        this.support = new PropertyChangeSupport(this);
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

    public String getCaseNom() {
        return nom.toLowerCase();
    }

    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        support.firePropertyChange("prix", oldNom, nom);
    }

    public String getPrix() {
        return prix;
    }

    public int getIntPrix() {
        return Integer.parseInt(prix);
    }

    public void setPrix(String prix) {
        String oldPrix = this.prix;
        this.prix = prix;
        support.firePropertyChange("prix", oldPrix, prix);
    }

    public String getDescription() {
        return description;
    }

    public String getCaseDescription() {
        return description.toLowerCase();
    }

    public void setDescription(String description) {
        String oldDecription = this.description;
        this.description = description;
        support.firePropertyChange("prix", oldDecription, description);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        String oldUrl = this.url;
        this.url = url;
        support.firePropertyChange("prix", oldUrl, url);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", idA=" + idA +
                ", nom='" + nom + '\'' +
                ", prix='" + prix + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", image=" + image +
                ", url=" + url +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Article other = (Article) obj;
        return this.id == other.id;
    }


}
