package org.example.Interfaces;
import org.example.Database.DBManager;
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
        JLabel etiquetaPassword = new JLabel("Mot de passe:");
        JLabel etiquetaTipoPersona = new JLabel("Profil utilisateur:");

        nom = new JTextField();
        prenom = new JTextField();
        campoPassword = new JPasswordField();

        String[] tiposPersona = {"Valideur", "Demandeur", "Benevole"};
        comboTipoPersona = new JComboBox<>(tiposPersona);

        botonCrearUsuario = new JButton("Creer utilisateur");

        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomText = nom.getText();
                String prenomText = prenom.getText();
                String password = new String(campoPassword.getPassword());
              //  String identificador = campoIdentificador.getText(); //
                int tipoPersona = comboTipoPersona.getSelectedIndex() + 1; // Convertir el índice del JComboBox a 1, 2 o 3
                DBManager db = DBManager.getInstance();
                int id=0;
                // Llama a un método para guardar los datos en la base de datoaucun de ses répertoires parents (jusqu'au points (debes implementar esta lógica)
                if (db.insertUser(id,nomText, prenomText, password, tipoPersona)) {
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