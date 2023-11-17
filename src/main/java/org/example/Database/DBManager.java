package org.example.Database;

import java.sql.*;


public class DBManager {

    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020";
    static String username = "projet_gei_020";
    static String password = "Ahlah6ug";
    private static Connection connection;
    private static DBManager instance;

    private DBManager() {
        connection = DBManager.Connection();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Connection Connection() {

        if (connection == null) {
            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return connection;
    }


    public boolean guardarUsuarioEnBD(String usuario, String password, String identificador, int tipoPersona) {
       Connection connection = Connection();
        try  {
            String sql = "INSERT INTO Utilisateur (nombre_usuario, contrasena, identificador, tipo_persona) VALUES (?, ?, ?, ?)";
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

        public void CreateTable(){
            try {
                Connection conn = Connection();
                Statement stmt = conn.createStatement();
                //étape 4: exécuter la requéte
                String sql = "CREATE TABLE Utilisateur " +
                        " (nom VARCHAR(255), " +
                        " password VARCHAR(255), " +
                        "id INTEGER , " +
                        " type INTEGER)";
                stmt.executeUpdate(sql);
                System.out.println("Table créée avec succés...");
                //étape 5: fermez l'objet de connexion
                conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }


        public static void main(String[] args) {
            // Test de la connexion
            Connection connection = Connection();

            if (connection != null) {
                System.out.println("Connexion réussie !");
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
