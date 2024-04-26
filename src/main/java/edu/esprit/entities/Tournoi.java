package edu.esprit.entities;
import java.sql.Date;

public class Tournoi {

    public int id;
    public int tournoi_id;
    public String type;
    public Jeu jeuId;
    public Date date;
    public int count;

    public Tournoi() {
    }

    public Tournoi(int tournoi_id, String type, Date date, Jeu jeuId,int count) {
        this.tournoi_id = tournoi_id;
        this.type = type;
        this.jeuId = jeuId;
        this.date = date;
        this.count = count;
    }

    public Tournoi(int id, int tournoi_id, String type, int participationId, Date date, int count, Jeu jeuId) {
        this.id = id;
        this.tournoi_id = tournoi_id;
        this.type = type;
        this.date = date;
        this.count = count;
        this.jeuId = jeuId;
    }
    public Tournoi(int id, int tournoi_id, String type, Date date, int count, Jeu jeuId) {
        this.id = id;
        this.tournoi_id = tournoi_id;
        this.type = type;
        this.date = date;
        this.count = count;
        this.jeuId = jeuId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTournoiId() {
        return tournoi_id;
    }

    public void setTournoiId(int tournoi_id) {
        this.tournoi_id = tournoi_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Jeu getJeuID() {
        return jeuId;
    }

    public void setJeuID(Jeu jeuID) {
        this.jeuId = jeuID;
    }

    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", tournoi_id=" + tournoi_id +
                ", type='" + type + '\'' +
                ", jeuId=" + jeuId +
                ", date=" + date +
                ", count=" + count +
                '}';
    }
}
