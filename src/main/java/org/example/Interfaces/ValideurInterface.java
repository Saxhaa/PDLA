package org.example.Interfaces;

import org.example.Database.DBManager;
import org.example.Mission.EnumMission;
import org.example.Mission.Mission;
import org.example.Users.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ValideurInterface extends JFrame {
    private JTextField dateField;
    private JTextField regionField;
    private JTextField descriptionField;
    private JTextArea benevoleRequestTextArea;
    private JTextArea demandeurRequestsTextArea;

    private Utilisateur utilisateur;
    private JButton validerDemandeButton;
    private JButton validerPropositionButton;

    public ValideurInterface() {
        initializeUI();
    }

    public ValideurInterface(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        initializeUI();
    }

    DBManager db = DBManager.getInstance();

    private void initializeUI() {
        setTitle("Interface Benevole");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));

        // Colonne 1
        JPanel requestPanel = new JPanel(new GridLayout(5, 1));

        JLabel userLabel = new JLabel("Bienvenue, " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        userLabel.setHorizontalAlignment(SwingConstants.LEFT);
        requestPanel.add(userLabel);

        // Ajout de boutons pour valider les demandes et propositions
        validerDemandeButton = new JButton("Valider Demande");
        validerDemandeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               validerDemandeSelectionnee();
            }
        });
        requestPanel.add(validerDemandeButton);

        validerPropositionButton = new JButton("Valider Proposition");
        validerPropositionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validerPropositionSelectionnee();
            }
        });
        requestPanel.add(validerPropositionButton);

        // Colonne 2
        demandeurRequestsTextArea = new JTextArea();
        demandeurRequestsTextArea.setBorder(BorderFactory.createTitledBorder("Demandes d'aide en attente de validation"));
        add(new JScrollPane(demandeurRequestsTextArea));
        List<Mission> demandes = db.getAlldemandesEnAttente();

        for (Mission mission : demandes) {

            demandeurRequestsTextArea.append("Demande d'aide - Date : " + mission.getDate() +
                    ", Région : " + mission.getRegion() +
                    ", Description : " + mission.getDescription() + "\n");
        }

        // Colonne 3
        benevoleRequestTextArea = new JTextArea();
        benevoleRequestTextArea.setBorder(BorderFactory.createTitledBorder("Propositions en attente"));
        add(new JScrollPane(benevoleRequestTextArea));
        List<Mission> userMissions = db.getAllpropositionsEnAttente();


        for (Mission userMission : userMissions) {
            benevoleRequestTextArea.append("Propositions d'aide  - Date : " + userMission.getDate() +
                    ", Région : " + userMission.getRegion() +
                    ", Description : " + userMission.getDescription() +
                    ", Statut : " + userMission.getStatut() + "\n");
        }

        add(requestPanel);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void validerDemandeSelectionnee() {
        // Récupérer la demande sélectionnée dans demandeurRequestsTextArea
        String demandeSelectionnee = demandeurRequestsTextArea.getSelectedText();

        // Effectuer la logique de validation ici
        Mission demande = getMissionFromText(demandeSelectionnee);
        if (demande != null) {
            // Mettre à jour le statut de la mission dans la base de données
            db.updateStatutMission(demande,2); // 2 pour "validée"
            JOptionPane.showMessageDialog(this, "Demande validée avec succès", "Validation", JOptionPane.INFORMATION_MESSAGE);
            // Actualiser l'affichage
           // actualiserDemandesEnAttente();
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une demande à valider", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validerPropositionSelectionnee() {
        // Récupérer la demande sélectionnée dans demandeurRequestsTextArea
        String propositionSelectionnee = benevoleRequestTextArea.getSelectedText();

        // Effectuer la logique de validation ici
        Mission proposition = getMissionFromText(propositionSelectionnee);
        if (proposition != null) {
            // Mettre à jour le statut de la mission dans la base de données
            db.updateStatutMission(proposition,2); // 2 pour "validée"
            JOptionPane.showMessageDialog(this, "Demande validée avec succès", "Validation", JOptionPane.INFORMATION_MESSAGE);
            // Actualiser l'affichage
            // actualiserDemandesEnAttente();
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une demande à valider", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private Mission getMissionFromText(String missionText) {
        // Exemple de chaîne : "Demande d'aide - Date : 18/19, Région : toulouse, Description : déménagement"
        Mission mission = new Mission();

        String[] parts = missionText.split(", ");

        for (String part : parts) {
            if (part.startsWith("Date : ")) {
                mission.setDate(part.substring("Date : ".length()));
            } else if (part.startsWith("Région : ")) {
                mission.setRegion(part.substring("Région : ".length()));
            } else if (part.startsWith("Description : ")) {
                mission.setDescription(part.substring("Description : ".length()));
            }
        }

        return mission;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DemandeurInterface();
            }
        });
    }
}