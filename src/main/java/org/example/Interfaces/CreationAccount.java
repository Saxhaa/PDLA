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
    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JTextField campoIdentificador;
    private JComboBox<String> comboTipoPersona;
    private JButton botonCrearUsuario;

    public CreationAccount() {
        setTitle("Creation du compte utilisateur");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        JLabel etiquetaUsuario = new JLabel("Nom de l'utilisateur:");
        JLabel etiquetaPassword = new JLabel("Mot de passe:");
        JLabel etiquetaIdentificador = new JLabel("Identifiant:");
        JLabel etiquetaTipoPersona = new JLabel("Profil utilisateur:");

        campoUsuario = new JTextField();
        campoPassword = new JPasswordField();
        campoIdentificador = new JTextField();

        String[] tiposPersona = {"Valideur", "Demandeur", "Benevole"};
        comboTipoPersona = new JComboBox<>(tiposPersona);

        botonCrearUsuario = new JButton("Creer utilisateur");

        botonCrearUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = campoUsuario.getText();
                String password = new String(campoPassword.getPassword());
                String identificador = campoIdentificador.getText(); //
                int tipoPersona = comboTipoPersona.getSelectedIndex() + 1; // Convertir el índice del JComboBox a 1, 2 o 3
                DBManager db = DBManager.getInstance();
                // Llama a un método para guardar los datos en la base de datoaucun de ses répertoires parents (jusqu'au points (debes implementar esta lógica)
                if (db.guardarUsuarioEnBD(usuario, password, identificador, tipoPersona)) {
                    JOptionPane.showMessageDialog(null, "Usuario creado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur lors de la creation de l'utilisateur.");
                }
            }
        });

        add(etiquetaUsuario);
        add(campoUsuario);
        add(etiquetaPassword);
        add(campoPassword);
        add(etiquetaIdentificador);
        add(campoIdentificador);
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