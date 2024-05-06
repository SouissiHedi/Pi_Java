package edu.esprit.entities;

public class Participation {

    public int id;

    public int tournoi_id;


   public Participation(){

   }

    public Participation(int id, int id_tournoi_id) {
        this.id = id;
        this.tournoi_id = id_tournoi_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tournoi_id() {
        return tournoi_id;
    }

    public void setId_tournoi_id(int id_tournoi_id) {
        this.tournoi_id = id_tournoi_id;
    }
    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", id_tournoi_id=" + tournoi_id +
                '}';
    }

}
