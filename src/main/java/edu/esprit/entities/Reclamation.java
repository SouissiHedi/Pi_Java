package edu.esprit.entities;

public class Reclamation {
    private Integer id;
    private String description;
    private String type;





    public Reclamation() {

    }
    public Reclamation(int id,String question,String type) {
        this.id = id;
        this.description= question;
        this.type = type;


    }





    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }







}

