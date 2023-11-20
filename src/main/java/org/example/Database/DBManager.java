package org.example.Database;

import org.example.Users.Utilisateur;

import java.sql.*;

public class DBManager {

    static String driver = "com.mysql.cj.jdbc.Driver";
    static String url = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_020";
    static String username = "projet_gei_020";
    static String password = "Ahlah6ug";
    private static Connection connection;

    private static int tableUser;
    private static int tableMission;
    private static DBManager instance;

    private DBManager() {
        connection = DBManager.Connection();
        this.CreateTableMission();
        this.CreateTableUser();
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


    public boolean insertUser(int id,String nom, String prenom, String mail, String password, int type) {
        PreparedStatement stmt = null;
        try  {
            String sql = "INSERT INTO User (id,nom,prenom, mail,password, type) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, mail);
            stmt.setString(5, password);
            stmt.setInt(6, type);
//test
            int filasAfectadas = stmt.executeUpdate();
            tableMission=1;
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public void CreateTableMission(){ //la estoy haciendo
        try {
            Connection conn = Connection();
            Statement stmt = conn.createStatement();
            //étape 4: exécuter la requéte
            String sql = "CREATE TABLE Mission " +
                    "(type VARCHAR(255), " +
                    " description VARCHAR(255), " +
                    " date VARCHAR(255), " +
                    " region VARCHAR(255), " +
                    " idMission INTEGER, " +
                    " idUtilisateur INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Table créée Mission avec succés...");

        } catch (Exception e) {
            System.out.println(e);
        }
    }



    public boolean insertMission(String description, String date, String region, int idMission, int idUtilisateur, String type) {
        PreparedStatement stmt = null;
        try  {
            String sql = "INSERT INTO Mission (type,description,date,region ,idMission, idUtilisateur) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1,type );
            stmt.setString(2, description);
            stmt.setString(3,date);
            stmt.setString(4, region);
            stmt.setInt(5, idMission);
            stmt.setInt(6, idUtilisateur);
//test
            int filasAfectadas = stmt.executeUpdate();
            tableMission=1;
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean CreateTableUser(){
        try {
            Connection conn = Connection();
            Statement stmt = conn.createStatement();
            //étape 4: exécuter la requéte
            String sql = "CREATE TABLE User " +
                    "(id INTEGER," +
                    " nom VARCHAR(255), " +
                    " prenom VARCHAR(255), " +
                    " mail VARCHAR(255), " +
                    " password VARCHAR(255), " +
                    " type INTEGER)";
            stmt.executeUpdate(sql);
            System.out.println("Table User créée avec succés...");
            tableUser=1;

        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }


    // Méthode pour récupérer un utilisateur par son identifiant
    public Utilisateur getUserByConn(String mail, String password) {
        Utilisateur utilisateur = null;
            String sql = "SELECT * FROM User WHERE mail = ? and password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, mail);
                preparedStatement.setString(2, password);
                ResultSet res=preparedStatement.executeQuery();
                if(res.next()){
                    System.out.println("Utilisateur authentifié");
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        utilisateur = new Utilisateur();
                        utilisateur.setId(resultSet.getInt("id"));
                        utilisateur.setNom(resultSet.getString("nom"));
                        utilisateur.setPrenom(resultSet.getString("prenom"));
                        utilisateur.setMail(resultSet.getString("mail"));
                        utilisateur.setPassword(resultSet.getString("password"));
                        utilisateur.setType(resultSet.getInt("type"));
                    }
                }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération de l'utilisateur.");
        }

        return utilisateur;
    }//

    public static void main(String[] args) {


    }
}

//mysql -h srv-bdens.insa-toulouse.fr --port=3306 -u projet_gei_020  -p projet_gei_020
//Ahlah6ug
