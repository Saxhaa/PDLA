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

public class BenevoleInterface extends JFrame {
    private JTextField dateField;
    private JTextField regionField;
    private JTextField descriptionField;
    private JTextArea benevoleRequestsTextArea;
    private JTextArea statusTextArea;

    private Utilisateur utilisateur;

    public BenevoleInterface() {
        initializeUI();
    }

    public BenevoleInterface(Utilisateur utilisateur) {
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

        dateField = new JTextField();
        dateField.setBorder(BorderFactory.createTitledBorder("Disponibilités"));
        requestPanel.add(dateField);

        regionField = new JTextField();
        regionField.setBorder(BorderFactory.createTitledBorder("Région"));
        requestPanel.add(regionField);

        descriptionField = new JTextField();
        descriptionField.setBorder(BorderFactory.createTitledBorder("Description"));
        requestPanel.add(descriptionField);

        JButton requestHelpButton = new JButton("Porposer de l'aide");
        requestHelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = dateField.getText();
                String region = regionField.getText();
                String description = descriptionField.getText();
                DBManager db = DBManager.getInstance();
                // Création d'une instance de la mission
                Mission mission = new Mission(utilisateur, description, EnumMission.PROPOSITION, date, region);


                // Insertion de la mission dans la base de données
                if (db.insertMission(mission)) {
                    statusTextArea.append("Offre d'aide enregistrée avec succès.\n");
                } else {
                    statusTextArea.append("Erreur lors de l'enregistrement de l'offre d'aide.\n");
                }

                statusTextArea.append("Demande d'aide - Date : " + date + ", Région : " + region +
                        ", Description : " + description + " Statut :" +mission.getStatut()+"\n");
            }
        });
        requestPanel.add(requestHelpButton);

        // Colonne 2
        benevoleRequestsTextArea = new JTextArea();
        benevoleRequestsTextArea.setBorder(BorderFactory.createTitledBorder("Demandes d'aide"));
        add(new JScrollPane(benevoleRequestsTextArea));
        List<Mission> demandes = db.getAlldemandes();

        for (Mission mission : demandes) {

            statusTextArea.append("Proposition d'aide - Date : " + mission.getDate() +
                    ", Région : " + mission.getRegion() +
                    ", Description : " + mission.getDescription() );
        }

        // Colonne 3
        statusTextArea = new JTextArea();
        statusTextArea.setBorder(BorderFactory.createTitledBorder("Etat de mes propositions"));
        add(new JScrollPane(statusTextArea));
        List<Mission> userMissions = db.getMissionsByUser(utilisateur.getMail());


        for (Mission userMission : userMissions) {
            statusTextArea.append("Propositions d'aide  - Date : " + userMission.getDate() +
                    ", Région : " + userMission.getRegion() +
                    ", Description : " + userMission.getDescription() +
                    ", Statut : " + userMission.getStatut() + "\n");
        }

        add(requestPanel);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
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