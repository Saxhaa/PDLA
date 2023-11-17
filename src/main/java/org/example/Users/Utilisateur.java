package org.example.Users;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String mail;
    private String password;

    private int id;

    public Utilisateur (String nom, String prenom, String mail, String password, int id){
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mail = mail;
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public int getId() {return this.id;    }

    public String getMail() {
        return this.mail;
    }
}
//