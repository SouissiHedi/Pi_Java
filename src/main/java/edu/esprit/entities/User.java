package edu.esprit.entities;

import java.sql.Blob;

public class User {
    public int id;
    public String email;
    public String roles;
    public String password;
    public Blob image;

    public User() {
    }

    public User(int id, String email, String roles, String password, Blob image) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + roles + '\'' +
                '}';
    }
}
