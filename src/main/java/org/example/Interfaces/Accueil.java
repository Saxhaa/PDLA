package org.example.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Accueil extends JFrame {
    private JButton boutonCreationCompte;
    private JButton boutonConnexion;


    public Accueil() {
        setTitle("Page d'accueil");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));

        boutonCreationCompte = new JButton("Créer un compte utilisateur");

        boutonCreationCompte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de création de compte lorsqu'on clique sur le bouton
                new CreationAccount();
                dispose();  // Fermer l'interface d'accueil si nécessaire
            }
        });

        boutonConnexion = new JButton("Connexion");

        boutonConnexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ouvrir l'interface de création de compte lorsqu'on clique sur le bouton
                new Connexion();
                dispose();  // Fermer l'interface d'accueil si nécessaire
            }
        });

        add(new JLabel("Bienvenue sur la page d'accueil"));
        add(boutonCreationCompte);
        add(boutonConnexion);


        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Accueil();
            }
        });
    }
}
//TEST