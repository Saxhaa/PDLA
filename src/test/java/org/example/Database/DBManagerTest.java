package org.example.Database;

import org.example.Database.DBManager;
import org.example.Mission.Mission;
import org.example.Users.Utilisateur;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
class DBManagerTest {
   // @Test
    /*public void testConnection() {
        Connection connection = db.Connection();

        if (connection != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Échec de la connexion.");
            fail("La connexion a échoué");
        }
    }*/
   private static DBManager db;
    @BeforeAll
    public static void setUp() throws Exception {
        db = DBManager.getInstance();
    }

    @AfterEach
    public void tearDown() throws Exception {
        // Supprime les tables après chaque test
        //db.CreateTableMission();
        //db.CreateTableUser();
    }

    @Test
    public void insertUserTest() {
        int id=1;
        assertTrue(db.insertUser(id, "nomTest", "prenomTest", "mailTest", "passwordTest", 1));

        Utilisateur user = db.getUserByConn("mailTest", "passwordTest");

        if (user == null) {
            fail("L'utilisateur n'a pas été trouvé dans la base de données.");
        }
        assertEquals("nomTest", user.getNom());
        assertEquals("prenomTest", user.getPrenom());
        assertEquals("mailTest", user.getMail());
        assertEquals("passwordTest", user.getPassword());
        assertEquals(1, user.getType());
        assertEquals(id, user.getId());

    }

    @Test
    public void testInsertMission() {

        // Insérer une mission dans la base de données
        assertTrue(db.insertMission("descriptiontest", "17/23test", "regionTest", 1, 2, "typetest"));

        // Récupérer la mission de la base de données par requête SQL
        Mission mission = null;
        try {
            Connection conn = db.Connection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM Mission WHERE description = 'descriptiontest'";
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                // Créer un objet Mission à partir des résultats de la requête
                mission = new Mission();
                mission.setDescription(rs.getString("description"));
                mission.setDate(rs.getString("date"));
                mission.setRegion(rs.getString("region"));
               // mission.setUtilisateur(rs.getInt("idUtilisateur"));
                mission.setIdTypeMission(rs.getInt("idMission"));
               // mission.setTypeMission(rs.getString("typeMission"));
            }

        } catch (Exception e) {
            fail("Exception: " + e);
        }

        // Vérifier que la mission a été récupérée avec succès
        assertNotNull(mission, "La mission doit être présente dans la base de données.");
        assertEquals("descriptiontest", mission.getDescription());
        assertEquals("17/23test", mission.getDate());
        assertEquals("regionTest", mission.getRegion());
       // assertEquals(1, mission.getIdUtilisateur());
        //assertEquals(2, mission.getIdTypeMission());
      //  assertEquals("typetest", mission.getTypeMission());
    }


    @Test
    public void testCreateTableMission() {
        //db.CreateTableMission();
        try {
            Connection conn = db.Connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'Mission'");
            assertTrue(rs.next(), "La table Mission a été créée avec succès.");
        } catch (Exception e) {
            fail("Exception: " + e);
        }

    }


    /*@Before
    public void setUp() throws SQLException {
        db = new DBManager();

        // Connexion à une base de données en mémoire H2 pour tester
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        db.setConnection(connection);
    }*/

    @Test
    public void testCreateTableUser() {
        //db.CreateTableMission();
        try {
            Connection conn = db.Connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'User'");
            assertTrue(rs.next(), "La table User a été créée avec succès.");
        } catch (Exception e) {
            fail("Exception: " + e);
        }
    }


    @Test
    public void testGetUserByConn() {
        // Insert a test user into the User table
        assertTrue(db.insertUser(1, "John", "Dupont", "exemple@gmail.com", "abcd", 1));

        // Test the getUserByConn method
        Utilisateur retrievedUser = db.getUserByConn("exemple@gmail.com", "abcd");

        assertNotNull(retrievedUser);
        assertEquals(1, retrievedUser.getId());
        assertEquals("John", retrievedUser.getNom());
        assertEquals("Dupont", retrievedUser.getPrenom());
        assertEquals("exemple@gmail.com", retrievedUser.getMail());
        assertEquals("abcd", retrievedUser.getPassword());
        assertEquals(1, retrievedUser.getType());
    }


}