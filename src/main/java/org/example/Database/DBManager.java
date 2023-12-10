package org.example.Database;

import org.example.Mission.Mission;
import org.example.Users.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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


    public boolean isEmailAlreadyUsed(String email) {
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT COUNT(*) FROM User WHERE mail = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean CreateTableMission(){
        try {
            Connection conn = Connection();
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE Mission " +
                    "(mailUser VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "date VARCHAR(255), " +
                    "region VARCHAR(255), "+
                    "mailRepondeur VARCHAR(255), "+
                    "statut INTEGER)";

            stmt.executeUpdate(sql);
            System.out.println("Table créée Mission avec succés...");
            tableMission = 1;
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public boolean CreateTableUser() {
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
            tableUser = 1;
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }



    public boolean insertMission(Mission mission) {
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO Mission (mailUser, description, date, region, mailRepondeur ,statut) " + "VALUES (?,?, ?, ?, ?,?)";
            stmt= connection.prepareStatement(sql);

            stmt.setString(1, mission.getUser().getMail());
            stmt.setString(2, mission.getDescription());
            stmt.setString(3, mission.getDate());
            stmt.setString(4, mission.getRegion());
            stmt.setString(5, "-");
            stmt.setInt(6, mission.getStatut());

            int res = stmt.executeUpdate();
            return res > 0;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean insertUser(int id,String nom, String prenom, String mail, String password, int type) {
        PreparedStatement stmt = null;
        if (isEmailAlreadyUsed(mail)) {
            System.out.println("Cet email est déjà associé à un utilisateur. Veuillez utiliser un autre email.");
            return true;
        }
        try  {
            String sql = "INSERT INTO User (id,nom,prenom, mail,password, type) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, nom);
            stmt.setString(3, prenom);
            stmt.setString(4, mail);
            stmt.setString(5, password);
            stmt.setInt(6, type);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }





    // Méthode pour récupérer un utilisateur par son identifiant
    public Utilisateur getUserByConnexion(String mail, String password) {
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




    public void deleteTestUser() {
        String sql = "DELETE FROM User WHERE mail LIKE 'mailTest%'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTestMission() {
        String sql = "DELETE FROM Mission WHERE description LIKE 'descriptionTest%'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Utilisateur getUserByEmail(String email) {
        PreparedStatement stmt = null;
        Utilisateur utilisateur = null;
        try {
            String sql = "SELECT * FROM User WHERE mail = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);

            try (ResultSet resultSet = stmt.executeQuery()) {
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
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return utilisateur;
    }

    public List<Mission> getMissionsByUser(String userEmail) {
        List<Mission> missions = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Mission WHERE mailUser = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, userEmail);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Mission mission = new Mission();
                    mission.setUser(getUserByEmail(userEmail));
                    mission.setDescription(resultSet.getString("description"));
                    mission.setDate(resultSet.getString("date"));
                    mission.setRegion(resultSet.getString("region"));
                    mission.setStatut(resultSet.getInt("statut"));
                    missions.add(mission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return missions;
    }


    public List<Mission> getAllPropositions() {

        List<Mission> missions = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Mission M JOIN User U ON M.mailUser = U.mail WHERE U.type = ? AND M.statut = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 2); //type user 2 = benevole
            stmt.setInt(2, 2); //type mission 2 = validée

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Mission mission = new Mission();
                    mission.setUser(getUserByEmail(resultSet.getString("mailUser")));
                    mission.setDescription(resultSet.getString("description"));
                    mission.setDate(resultSet.getString("date"));
                    mission.setRegion(resultSet.getString("region"));
                    mission.setStatut(resultSet.getInt("statut"));
                    missions.add(mission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return missions;
    }

    public List<Mission> getAlldemandes() {
        List<Mission> missions = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Mission M JOIN User U ON M.mailUser = U.mail WHERE U.type = ? AND M.statut = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 1); //type user 2 = demandeur
            stmt.setInt(2, 2); //type mission 2 = validée

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Mission mission = new Mission();
                    mission.setUser(getUserByEmail(resultSet.getString("mailUser")));
                    mission.setDescription(resultSet.getString("description"));
                    mission.setDate(resultSet.getString("date"));
                    mission.setRegion(resultSet.getString("region"));
                    mission.setStatut(resultSet.getInt("statut"));
                    missions.add(mission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return missions;
    }

    public List<Mission> getAlldemandesEnAttente() {
        List<Mission> missions = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Mission M JOIN User U ON M.mailUser = U.mail WHERE U.type = ? AND M.statut = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 1); //type user 1 = demandeur
            stmt.setInt(2, 1); //type mission 1 = en attente

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Mission mission = new Mission();
                    mission.setUser(getUserByEmail(resultSet.getString("mailUser")));
                    mission.setDescription(resultSet.getString("description"));
                    mission.setDate(resultSet.getString("date"));
                    mission.setRegion(resultSet.getString("region"));
                    mission.setStatut(resultSet.getInt("statut"));
                    missions.add(mission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return missions;
    }

    public List<Mission> getAllpropositionsEnAttente() {
        List<Mission> missions = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            String sql = "SELECT * FROM Mission M JOIN User U ON M.mailUser = U.mail WHERE U.type = ? AND M.statut = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, 2); //type user 2= benevole
            stmt.setInt(2, 1); //type mission 1 = en attente

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Mission mission = new Mission();
                    mission.setUser(getUserByEmail(resultSet.getString("mailUser")));
                    mission.setDescription(resultSet.getString("description"));
                    mission.setDate(resultSet.getString("date"));
                    mission.setRegion(resultSet.getString("region"));
                    mission.setStatut(resultSet.getInt("statut"));
                    missions.add(mission);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return missions;
    }

    public void updateStatutMission(Mission demande, int i) { demande.setStatut(i); }
}



//mysql -h srv-bdens.insa-toulouse.fr --port=3306 -u projet_gei_020  -p projet_gei_020
//Ahlah6ug
