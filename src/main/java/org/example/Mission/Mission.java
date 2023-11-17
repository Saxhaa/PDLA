package org.example.Mission;

import org.example.Users.Utilisateur;

public class Mission {
    private String description;
    private EnumMission type;

    private String date;

    private String region;

    private int id;

    private Utilisateur user;

    private static int totalMission;

    // Constructeur avec le type de mission en plus de la description
    public Mission(Utilisateur user, String description, EnumMission type, String date, String region, int id) {
        this.description = description;
        this.type = type;
        this.date=date;
        this.region=region;
        this.id=totalMission++;
        this.user=user;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Utilisateur getUser(){ return user;}

    public int getId() {
        return id;
    }

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


}
//