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
                System.out.println("connexion reussie à la bdd");
            }  catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
        return connection;
    }


    public boolean insertUser(int id,String nom, String prenom, String mail, String password, int tipoPersona) {

        try  {
            String sql = "INSERT INTO User (id,nom,prenom, password, tipo_persona) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mail);
            preparedStatement.setString(5, password);
            preparedStatement.setInt(6, tipoPersona);
//
            int filasAfectadas = preparedStatement.executeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public void CreateTableUser(){
            try {
                Connection conn = Connection();
                Statement stmt = conn.createStatement();
                //étape 4: exécuter la requéte
                String sql = "CREATE TABLE User " +
                        "(id INTEGER," +
                        " nom VARCHAR(255), " +
                        " prenom VARCHAR(255), " +
                        " password VARCHAR(255), " +
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
                DBManager db = DBManager.getInstance();
                db.CreateTableUser();


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
