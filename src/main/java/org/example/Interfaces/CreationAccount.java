package org.example.Interfaces;
import org.example.Database.DBManager;
import org.example.Users.Utilisateur;
import org.example.Users.enumUtilisateur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//
public class CreationAccount extends JFrame {
    private JTextField nom;
    private JTextField prenom;
    private JTextField mail;
    private JPasswordField campoPassword;
    private JComboBox<String> comboTipoPersona;
    private JButton botonCrearUsuario;

    public CreationAccount() {
        setTitle("Creation du compte utilisateur");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel etiquetaNom = new JLabel("Nom de l'utilisateur:");
        JLabel etiquetaPrenom = new JLabel("Prenom de l'utilisateur:");
        JLabel etiquetaMail = new JLabel("Mail de l'utilisateur:");
        JLabel etiquetaPassword = new JLabel("Mot de passe:");
        JLabel etiquetaTipoPersona = new JLabel("Profil utilisateur:");

        nom = new JTextField();
        prenom = new JTextField();
        mail = new JTextField();
        campoPassword = new JPasswordField();

        String[] tiposPersona = {"Valideur", "Demandeur", "Benevole"};
        comboTipoPersona = new JComboBox<>(tiposPersona);

        botonCrearUsuario = new JButton("Creer utilisateur");

        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomText = nom.getText();
                String prenomText = prenom.getText();
                String mailText = mail.getText();
                String password = new String(campoPassword.getPassword());
                int tipoPersona = comboTipoPersona.getSelectedIndex() + 1;// Convertir el índice del JComboBox a 1, 2 o 3

                //si on veut inserer le type et pas un nb
                /*enumUtilisateur type;
                if (tipoPersona==1)
                    type=enumUtilisateur.valideur;
                else if (tipoPersona==2)
                    type=enumUtilisateur.demandeur;
                else if (tipoPersona==3)
                    type=enumUtilisateur.benevole;*/

                DBManager db = DBManager.getInstance();
                Utilisateur util = new Utilisateur(nomText, prenomText, mailText, password, tipoPersona);

                // Llama a un método para guardar los datos en la base de datoaucun de ses répertoires parents (jusqu'au points (debes implementar esta lógica)
                if (db.insertUser(util.getId(), nomText, prenomText, mailText, password, tipoPersona)) {
                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la creation de l'utilisateur.");
                }
            }
        });

        add(etiquetaNom);
        add(nom);
        add(etiquetaPrenom);
        add(prenom);
        add(etiquetaMail);
        add(mail);
        add(etiquetaPassword);
        add(campoPassword);
        //  add(etiquetaIdentificador);
        // add(campoIdentificador);
        add(etiquetaTipoPersona);
        add(comboTipoPersona);
        add(new JLabel(""));
        add(botonCrearUsuario);

        setVisible(true);
    }

    // Método para guardar los datos en la base de datos (debes implementar esta lógica)


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreationAccount();
            }
        });
    }
}

//Valideur=1
//Demandeur=2
//Benevole=3
//
