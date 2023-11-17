package org.example.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DemandeurInterface extends JFrame {
    private JTextArea helpNeededTextArea;
    private JTextArea benevoleRequestsTextArea;
    private JTextArea statusTextArea;

    public DemandeurInterface() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Interfaz para Demandeur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));

        // Caja para escribir sobre la ayuda que se necesita
        helpNeededTextArea = new JTextArea();
        JScrollPane helpNeededScrollPane = new JScrollPane(helpNeededTextArea);
        helpNeededScrollPane.setBorder(BorderFactory.createTitledBorder("Ayuda Necesaria"));
        add(helpNeededScrollPane);

        // Caja para ver las solicitudes de los benevoles
        benevoleRequestsTextArea = new JTextArea();
        JScrollPane benevoleRequestsScrollPane = new JScrollPane(benevoleRequestsTextArea);
        benevoleRequestsScrollPane.setBorder(BorderFactory.createTitledBorder("Solicitudes de Benevoles"));
        add(benevoleRequestsScrollPane);

        // Caja para ver el estado de todas las ayudas propuestas
        statusTextArea = new JTextArea();
        JScrollPane statusScrollPane = new JScrollPane(statusTextArea);
        statusScrollPane.setBorder(BorderFactory.createTitledBorder("Estado de Ayudas Propuestas"));
        add(statusScrollPane);

        // Botón para solicitar ayuda
        JButton requestHelpButton = new JButton("Solicitar Ayuda");
        requestHelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para procesar la solicitud de ayuda
                String helpRequest = helpNeededTextArea.getText();
                // ... Lógica para procesar la solicitud de ayuda ...
                // Actualizar la caja de estado
                statusTextArea.append("Solicitud de Ayuda: " + helpRequest + " - En Proceso\n");
            }
        });
        add(requestHelpButton);

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