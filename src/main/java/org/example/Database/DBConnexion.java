package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.example.Database.DBConnexion.getConnection;


public class DBConnexion {
    public static Connection getConnection() throws ClassNotFoundException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020";
        String username = "projet_gei_020";
        String password = "Ahlah6ug";
        Connection connection = null;
        Class.forName(driver);
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



    public static void main(String[] args) throws ClassNotFoundException {
        // Chargement du pilote JDBC
        try {
            //Class.forName("/home/bonicatt/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar!/com/mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Test de la connexion
        Connection connection = getConnection();

        // Vérification de la connexion
        if (connection != null) {
            System.out.println("Connexion réussie !");

            // Ajoutez ici vos opérations sur la base de données, si nécessaire

            // Fermeture de la connexion après utilisation
            try {
                connection.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.out.println("Échec de la connexion. Message d'erreur : " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Échec de la connexion.");
        }
    }
}