package org.example.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BenevoleInterface extends JFrame {
    private JTextArea messageTextArea;
    private JTextArea requestsTextArea;
    private JTextArea statusTextArea;

    public BenevoleInterface() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Interfaz para Benevole");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3));

        // Caja para escribir sobre la ayuda que quieren dar
        messageTextArea = new JTextArea();
        JScrollPane messageScrollPane = new JScrollPane(messageTextArea);
        messageScrollPane.setBorder(BorderFactory.createTitledBorder("Ayuda a Ofrecer"));
        add(messageScrollPane);

        // Caja para ver las solicitudes de los demandantes
        requestsTextArea = new JTextArea();
        JScrollPane requestsScrollPane = new JScrollPane(requestsTextArea);
        requestsScrollPane.setBorder(BorderFactory.createTitledBorder("Solicitudes de Demandeur"));
        add(requestsScrollPane);

        // Caja para ver el estado de todas las ayudas propuestas
        statusTextArea = new JTextArea();
        JScrollPane statusScrollPane = new JScrollPane(statusTextArea);
        statusScrollPane.setBorder(BorderFactory.createTitledBorder("Estado de Ayudas Propuestas"));
        add(statusScrollPane);

        // Botón para proponer ayuda
        JButton proposeHelpButton = new JButton("Proponer Ayuda");
        proposeHelpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes agregar la lógica para procesar la ayuda propuesta
                String helpMessage = messageTextArea.getText();
                // ... Lógica para procesar la ayuda ...
                // Actualizar la caja de estado
                statusTextArea.append("Ayuda Propuesta: " + helpMessage + " - En Proceso\n");
            }
        });
        add(proposeHelpButton);

        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BenevoleInterface();
            }
        });
    }
}