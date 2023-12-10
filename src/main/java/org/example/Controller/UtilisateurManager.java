package org.example.Controller;

import org.example.Users.Utilisateur;

import java.util.ArrayList;
import java.util.List;

public class UtilisateurManager {
    private List<Utilisateur> utilisateurs;
    private static int totalUtilisateur = 0;

    public UtilisateurManager() {
        this.utilisateurs = new ArrayList<>();
    }

    public Utilisateur createUser(String nom, String prenom, String mail, String password, int type) {
        Utilisateur utilisateur = new Utilisateur(nom, prenom, mail, password, type);
        utilisateur.setId(getNextUserId()); // Affecte un nouvel ID à l'utilisateur
        utilisateurs.add(utilisateur); // Ajoute l'utilisateur à la liste
        return utilisateur;
    }

    private int getNextUserId() {
        return totalUtilisateur++;
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurs;
    }

    public Utilisateur getUserById(int userId) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getId() == userId) {
                return utilisateur;
            }
        }
        return null; // Utilisateur non trouvé
    }


}
