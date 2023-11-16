package org.example.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                String identificador = campoIdentificador.getText();
                int tipoPersona = comboTipoPersona.getSelectedIndex() + 1; // Convertir el índice del JComboBox a 1, 2 o 3

                // Llama a un método para guardar los datos en la base de datoaucun de ses répertoires parents (jusqu'au points (debes implementar esta lógica)
                if (guardarUsuarioEnBD(usuario, password, identificador, tipoPersona)) {
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
    private boolean guardarUsuarioEnBD(String usuario, String password, String identificador, int tipoPersona) {
        String servidor = "srv-bdens.insa-toulouse.fr:3306";
        String baseDeDatos = "projet_gei_020";
        String usuarioBD = "projet_gei_020";
        String contrasenaBD = "Ahlah6ug";
        String url = "jdbc:mysql://" + servidor + "/" + baseDeDatos;

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, contrasenaBD)) {
            String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, identificador, tipo_persona) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, identificador);
            preparedStatement.setInt(4, tipoPersona);

            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CreationAccount();
            }
        });
    }
}