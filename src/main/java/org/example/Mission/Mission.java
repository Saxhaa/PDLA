package org.example.Mission;

import org.example.Users.Utilisateur;

public class Mission {
    private String description;
    private EnumMission type;

    private String date;

    private String region;
    private int statut; // 1= en attente, 2= validée, 3= terminée

   // private int id;

    private Utilisateur user;
    private Utilisateur repondeur;

    private static int totalMission;

    // Constructeur avec le type de mission en plus de la description
    public Mission(Utilisateur user, String description, EnumMission type, String date, String region) {
        this.description = description;
        this.type = type;
        this.date=date;
        this.region=region;
        this.user=user;
        this.statut= 1;
        this.repondeur=null;

    }

    public Mission(){};


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUser(){ return user;}

   // public int getId() {return id;}

    public static int getTotalMission() {
        return totalMission;
    }

    public String getDate() {
        return date;
    }

    public String getRegion() {
        return region;
    }

    public EnumMission getType() {
        return type;
    }


    public void setDate(String date) { this.date=date;
    }

    public void setRegion(String region) { this.region=region;
    }

    public void setUtilisateur(Utilisateur user) { this.user=user;
    }

    //public void setIdTypeMission(int idMission) { this.id=idMission;    }

    public void setTypeMission(EnumMission typeMission) { this.type=typeMission;    }

    public int getStatut() { return this.statut; }

    public Utilisateur getRepondeur(){ return this.repondeur;}

    public void setStatut(int statut) { this.statut=statut;}

    public void setUser (Utilisateur User) { this.user=User;}
}
//