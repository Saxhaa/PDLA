package org.example.Database;

import org.example.Mission.EnumMission;
import org.example.Mission.Mission;
import org.example.Users.Utilisateur;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
class DBManagerTest {

   private static DBManager db;
    final Utilisateur userTest = new Utilisateur("nomTest", "prenomTest", "mailTest", "passwordTest", 1);
    final Mission missionTest =new Mission(userTest, "descriptionTest" , EnumMission.DEMANDE, "dateTest", "regionTest");

    @BeforeAll
    public static void setUp() throws Exception {
        db = DBManager.getInstance();
    }

    @AfterEach
    public void tearDown() {
        // Supprimer les données de test
        db.deleteTestUser();
        db.deleteTestMission();
    }



    @Test
    public void testCreateTableUser() {
        db.CreateTableUser();
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
    public void testCreateTableMission() {
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

    @Test
    public void insertUserTest() {

        assertTrue(db.insertUser(userTest.getId(),userTest.getNom(), userTest.getPrenom(), userTest.getMail(), userTest.getPassword(), userTest.getType()));

    }

  @Test
    public void testInsertMission() {

        assertTrue(db.insertMission(missionTest));

    }





}