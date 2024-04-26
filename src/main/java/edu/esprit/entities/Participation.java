package edu.esprit.entities;

public class Participation {

    public int id;

    public int id_tournoi_id;


   public Participation(){

   }

    public Participation(int id, int id_tournoi_id) {
        this.id = id;
        this.id_tournoi_id = id_tournoi_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tournoi_id() {
        return id_tournoi_id;
    }

    public void setId_tournoi_id(int id_tournoi_id) {
        this.id_tournoi_id = id_tournoi_id;
    }
    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", id_tournoi_id=" + id_tournoi_id +
                '}';
    }
}
