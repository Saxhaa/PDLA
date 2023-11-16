package org.example.Interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connexion extends JFrame {
    private JTextField champUser;
    private JPasswordField champPassword;
    private JTextField champId;
    private JLabel messageErreur;

    public Connexion() {
        // Configuration de la fenêtre
        setTitle("Formulaire d'Utilisateur");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Étiquettes
        JLabel labelUser = new JLabel("Nom d'utilisateur :");
        JLabel labelPassword = new JLabel("Mot de passe :");
        JLabel labelId = new JLabel("Identifiant :");

        // Champs de texte
        champUser = new JTextField();
        champPassword = new JPasswordField();
        champId = new JTextField();

        // Bouton de soumission
        JButton boutonSoumettre = new JButton("Soumettre");

        // Message d'erreur
        messageErreur = new JLabel("");
        messageErreur.setForeground(Color.RED);

        // Écouteur d'événements pour le bouton Soumettre
        boutonSoumettre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = champUser.getText();
                char[] password = champPassword.getPassword();
                String id = champId.getText();

                if (validerUtilisateur(user, new String(password), id)) {
                    //
                    JOptionPane.showMessageDialog(null, "Authentification réussie. Redirection vers la page...");
                } else {
                    // Afficher un message d'erreur
                    messageErreur.setText("Authentification échouée. Vérifiez vos données.");
                }
            }
        });

        // Ajout des composants à la fenêtre
        add(labelUser);
        add(champUser);
        add(labelPassword);
        add(champPassword);
        add(labelId);
        add(champId);
        add(boutonSoumettre);
        add(new JLabel("")); // Espace vide
        add(messageErreur);

        // Affichage de la fenêtre
        setVisible(true);
    }

    private boolean validerUtilisateur(String user, String password, String id) {
        // Configure la connexion de la BBDD
        String url = "jdbc:mysql://localhost:3306/projet_gei_020";
        String usuarioBD = "projet_gei_020";
        String contrasenaBD = "Ahlah6ug ";

        try (Connection connection = DriverManager.getConnection(url, usuarioBD, contrasenaBD)) {
            //Requête SQL pour vérifier les informations d'identification
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ? AND identificador = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            // S´il y a un résultat, les informations d'identification sont valides.
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Connexion();
            }
        });
    }
}

//Dans cet exemple, nous avons simulé la validation des données en utilisant une méthode validerUtilisateur().
//Vous devez remplacer cette méthode par une connexion réelle à votre base de données et effectuer la vérification des données.
//Si les données sont correctes, vous pouvez rediriger l'utilisateur vers la page souhaitée. Si les données sont incorrectes, un message d'erreur est affiché.
//N'oubliez pas de configurer correctement votre base de données dans votre application
//ah
