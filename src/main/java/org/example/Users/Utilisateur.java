package org.example.Users;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String mail;
    private String password;

    private int type;

    private static int totalUtilisateur = 0;

    private int id;

    public Utilisateur() {
    }
    public Utilisateur(String nom, String prenom, String mail, String password, int type) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.mail = mail;
        this.id = totalUtilisateur++;
        this.type = type;
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

    public int getId() {
        return this.id;
    }

    public String getMail() {
        return this.mail;
    }

    public int getType() {return this.type;}

    public void setId(int id) { this.id=id;  }

    public void setNom(String nom) { this.nom=nom;}


    public void setPrenom(String prenom) { this.prenom=prenom;
    }

    public void setPassword(String password) { this.password=password;
    }

    public void setMail(String mail) { this.mail=mail;
    }

    public void setType(int type) {this.type=type;
    }
}

//

//A faire
//une classe par type user
//dans demandeur : constructeur +une methode de demande d'aide
//valideur : constructeur + valider + renoncer
//....