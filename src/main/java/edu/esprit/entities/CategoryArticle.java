package edu.esprit.entities;

public class CategoryArticle {
    private int id;
    private String nomCat;

    public CategoryArticle() {
    }

    public CategoryArticle(String nomCat) {
        this.nomCat = nomCat;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    // toString method
    @Override
    public String toString() {
        return "CategoryArticle{" +
                "id=" + id +
                ", nomCat='" + nomCat + '\'' +
                '}';
    }
}
