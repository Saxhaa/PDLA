package org.example.Users;

public class Utilisateur {
    private String nom;
    private String prenom;
    private double telephone;
    private String mail;

    public Utilisateur (String nom, String prenom, double telephone, String mail){
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.mail = mail;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public double getTelephone() {
        return this.telephone;
    }

    public String getMail() {
        return this.mail;
    }
}