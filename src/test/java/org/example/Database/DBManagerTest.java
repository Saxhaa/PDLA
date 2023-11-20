package org.example.Database;

import org.example.Database.DBManager;
import org.example.Users.Utilisateur;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;



@RunWith(JUnit4.class)
class DBManagerTest {
    @Test
    public void testConnection() {
        Connection connection = DBManager.getInstance().Connection();

        if (connection != null) {
            System.out.println("Connexion réussie !");
        } else {
            System.out.println("Échec de la connexion.");
            fail("La connexion a échoué");
        }
    }

    @Test
    public void insertUserTest() {
        DBManager db = DBManager.getInstance();
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

    private DBManager db;
    @Before
    public void setUp() throws Exception {
        DBManager db = DBManager.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        // Supprime les tables après chaque test
        db.CreateTableMission();
        db.CreateTableUser();
    }

    @Test
    public void testCreateTableMission() {
        DBManager db = DBManager.getInstance();
        db.CreateTableMission();
        try {
            Connection conn = db.Connection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'Mission'");
            assertTrue(rs.next(), "La table Mission a été créée avec succès.");
        } catch (Exception e) {
            fail("Exception: " + e);
        }

    }

    private Connection connection;

    /*@Before
    public void setUp() throws SQLException {
        db = new DBManager();

        // Connexion à une base de données en mémoire H2 pour tester
        connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        db.setConnection(connection);
    }*/

    @Test
    public void testCreateTableUser() throws SQLException {
        DBManager db = DBManager.getInstance();
        db.CreateTableMission();
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
    public void testInsertMission() {
        boolean result = db.insertMission("Mission test", "2021-03-25", "Sud?", 1, 1, "Demande");
        assertTrue(result);
    }

    /*@Test
    public void testGetUserByConn() {
        // Testez la méthode getUserByConn ici
        User expectedUser = new User("user@email.com", "password", "John Doe");
        User actualUser = DBManager.getInstance().getUserByConn("user@email.com", "password");

        assertEquals(expectedUser, actualUser);
    } j'ai grave du mal je comprends pas donc à faire plus tard*/




}