package edu.esprit.entities;

import javafx.scene.image.Image;

public class JeuPartie {
    public int id;
    public int jeu_id;
    public int score;
    public int joueur_id;
    public String partie_type;

    public JeuPartie() {
    }

    public JeuPartie(int id, int jeu_id, int score, int joueur_id, String partie_type) {
        this.id = id;
        this.jeu_id = jeu_id;
        this.score = score;
        this.joueur_id = joueur_id;
        this.partie_type = partie_type;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getJoueur_id() {
        return joueur_id;
    }

    public void setJoueur_id(int joueur_id) {
        this.joueur_id = joueur_id;
    }

    public String getPartie_type() {
        return partie_type;
    }

    public void setPartie_type(String partie_type) {
        this.partie_type = partie_type;
    }

    @Override
    public String toString() {
        return "JeuPartie{" +
                "id=" + id +
                ", jeu_id=" + jeu_id +
                ", score='" + score + '\'' +
                ", joueur_id='" + joueur_id + '\'' +
                ", partie_type=" + partie_type +
                '}';
    }
}