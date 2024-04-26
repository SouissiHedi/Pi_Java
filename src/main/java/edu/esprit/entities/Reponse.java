package edu.esprit.entities;

public class Reponse {
    private Integer id;
    private String rep;

    public Reponse() {

    }

    public Reponse(Integer id, String rep) {
        this.id = id;
        this.rep = rep;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRep() {
        return rep;
    }

    public void setRep(String rep) {
        this.rep = rep;
    }
}
