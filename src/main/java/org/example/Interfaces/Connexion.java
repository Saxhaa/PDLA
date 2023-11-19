package org.example.Interfaces;

import org.example.Database.DBManager;
import org.example.Users.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connexion extends JFrame {
    private JTextField champMail;
    private JPasswordField champPassword;
    private JLabel messageErreur;

    public Connexion() {
        // Configuration de la fenêtre
        setTitle("Formulaire de Connexion");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Étiquettes
        JLabel labelMail = new JLabel("Adresse e-mail :");
        JLabel labelPassword = new JLabel("Mot de passe :");

        // Champs de texte
        champMail = new JTextField();
        champPassword = new JPasswordField();

        // Bouton de soumission
        JButton boutonConnexion = new JButton("Connexion");

        // Message d'erreur
        messageErreur = new JLabel("");
        messageErreur.setForeground(Color.RED);

        // Écouteur d'événements pour le bouton Connexion
        boutonConnexion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String mail = champMail.getText();
                char[] password = champPassword.getPassword();

                Utilisateur utilisateur = DBManager.getInstance().getUserByConn(mail, new String(password));

                if (utilisateur != null) {
                    JOptionPane.showMessageDialog(null, "Connexion réussie. Redirection... " + utilisateur.getNom() + " " + utilisateur.getPrenom() + "!");
                    if (utilisateur.getType() == 1) {  // Valideur
                        new ValideurInterface(utilisateur);
                    } else if (utilisateur.getType() == 2) {  // Demandeur
                        new DemandeurInterface(utilisateur);
                    } else if (utilisateur.getType() == 3) {  // Benevole
                        new BenevoleInterface(utilisateur);
                    }

                    // Fermer l'interface de création de compte
                    dispose();
                } else {
                    // Afficher un message d'erreur
                    messageErreur.setText("Connexion échouée. Vérifiez vos informations.");
                }
            }
        });

        // Ajout des composants à la fenêtre
        add(labelMail);
        add(champMail);
        add(labelPassword);
        add(champPassword);
        add(boutonConnexion);
        add(new JLabel("")); // Espace vide
        add(messageErreur);

        // Affichage de la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Connexion();
            }
        });
    }

}

/*
J'ai créé le main, a executer pour voir si tout marche

J'ai créé l'interface d'acceuil avant connexion ou inscription

reussite apres inscription d'acceder à l'interface benevole, valideur ou demandeur
mais PB : interface générale et pas celle de l'utilisateur personnalisée !!!!!!!

je n'ai pas reussi à me connecter avec un utilisateur existant

A faire :
- les test des fonctions qui marchent (celles dans DBManager)
- faire en sorte de créer des interfaces personnalisées à chaque utilisateur
( voir ses propres demandes en cours .. blabla)
-reussir à se connecter une fois le compte créé
 */
