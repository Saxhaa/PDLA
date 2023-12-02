package org.example.Interfaces;

import org.example.Users.Utilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValideurInterface extends JFrame {
    private JTextArea benevoleRequestsTextArea;
    private JTextArea demandeurRequestsTextArea;
    private JTextField benevoleApprovalField;
    private JTextField demandeurApprovalField;

    private Utilisateur utilisateur;
    public ValideurInterface( ) {
        initializeUI();
    }

    public ValideurInterface(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        initializeUI();
    }
    private void initializeUI() {
        setTitle("Interface Valideur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));

        // Caja para ver las solicitudes de los benevoles numeradas
        benevoleRequestsTextArea = new JTextArea();
        JScrollPane benevoleRequestsScrollPane = new JScrollPane(benevoleRequestsTextArea);
        benevoleRequestsScrollPane.setBorder(BorderFactory.createTitledBorder("Solicitudes de Benevoles"));
        add(benevoleRequestsScrollPane);

        // Caja para ver las solicitudes de los demandeurs numeradas
        demandeurRequestsTextArea = new JTextArea();
        JScrollPane demandeurRequestsScrollPane = new JScrollPane(demandeurRequestsTextArea);
        demandeurRequestsScrollPane.setBorder(BorderFactory.createTitledBorder("Solicitudes de Demandeurs"));
        add(demandeurRequestsScrollPane);

        // Caja para aprobar solicitudes
        JPanel approvalPanel = new JPanel(new GridLayout(3, 2));
        approvalPanel.setBorder(BorderFactory.createTitledBorder("Aprouver solicitude"));

        approvalPanel.add(new JLabel("Numero de Solicitude Benevole:"));
        benevoleApprovalField = new JTextField();
        approvalPanel.add(benevoleApprovalField);

        approvalPanel.add(new JLabel("Numero de Solicitude Demandeur:"));
        demandeurApprovalField = new JTextField();
        approvalPanel.add(demandeurApprovalField);

        JButton approveButton = new JButton("Aprouver solicitudes");
        approveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para aprobar las solicitudes
                int benevoleNumber = Integer.parseInt(benevoleApprovalField.getText());
                int demandeurNumber = Integer.parseInt(demandeurApprovalField.getText());

                // ... Lógica para aprobar las solicitudes ...
                // Actualizar las cajas de solicitudes
                benevoleRequestsTextArea.append("Solicitude " + benevoleNumber + " Aprouver\n");
                demandeurRequestsTextArea.append("Solicitude " + demandeurNumber + " Apeouver\n");
            }
        });

        approvalPanel.add(approveButton);

        add(approvalPanel);

        // Utilisez les informations de l'utilisateur pour personnaliser l'interface
        JLabel userLabel = new JLabel("Bienvenue, " + utilisateur.getNom() + " " + utilisateur.getPrenom());
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(userLabel);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ValideurInterface();
            }
        });
    }
}