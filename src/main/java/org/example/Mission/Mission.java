package org.example.Mission;

public class Mission {
    private String description;
    private EnumMission type;

    // Constructeur avec le type de mission en plus de la description
    public Mission(String description, EnumMission type) {
        this.description = description;
        this.type = type;

    }

    // Getter pour accéder à la description
    public String getDescription() {
        return description;
    }

    // Setter pour modifier la description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter pour accéder au type de mission
    public EnumMission getType() {
        return type;
    }

    // Setter pour modifier le type de mission
    public void setType(EnumMission type) {
        this.type = type;
    }
}
//