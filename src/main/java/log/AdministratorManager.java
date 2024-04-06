package log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bilioteca.DatabaseConnection;
/**
 * Clasa AdministratorManager gestionează operațiunile de înregistrare și autentificare a administratorilor.
 * Aceasta include citirea și scrierea datelor din/intr-un fișier și manipularea listei de administratori.
 */
/*public class AdministratorManager {

	private static final String INSERT_ADMIN_QUERY = "INSERT INTO administrators (username, password) VALUES (?, ?)";
    private static final String SELECT_ADMIN_QUERY = "SELECT * FROM administrators WHERE username = ? AND password = ?";
    
    /**
     * Înregistrează un nou administrator cu numele de utilizator și parola specificate.
     *
     * @param username Numele de utilizator pentru noul administrator.
     * @param password Parola pentru noul administrator.
     * @return True dacă înregistrarea a fost reușită, false dacă numele de utilizator există deja.
     */
   /* public boolean registerUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ADMIN_QUERY)) {
            if (checkIfUserExists(username)) {
                return false; // Numele de utilizator există deja
            }
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Autentifică un administrator cu numele de utilizator și parola specificate.
     *
     * @param username Numele de utilizator al administratorului.
     * @param password Parola administratorului.
     * @return True dacă autentificarea este reușită, false dacă numele de utilizator sau parola sunt invalide.
     */
/*
    public boolean loginUser(String username, String password) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ADMIN_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifică dacă un utilizator există deja în baza de date.
     *
     * @param username Numele de utilizator pentru verificare.
     * @return True dacă utilizatorul există, false altfel.
     * @throws SQLException în caz de eroare la interogare.
     *//*
    private boolean checkIfUserExists(String username) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT username FROM administrators WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }
}*/

public class AdministratorManager {

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/tres;create=true";
    private static final String INSERT_ADMIN_QUERY = "INSERT INTO administrators (username, password) VALUES (?, ?)";
    private static final String SELECT_ADMIN_QUERY = "SELECT * FROM administrators WHERE username = ? AND password =?";
    private static final String CREATE_ADMIN_TABLE_QUERY = "CREATE TABLE administrators (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, username VARCHAR(50) UNIQUE, password VARCHAR(50))";

    /*public AdministratorManager() {
        createAdminTableIfNotExists();
    }*/

    public boolean registerUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(INSERT_ADMIN_QUERY)) {
            if (checkIfUserExists(username)) {
                return false;
            }
            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(SELECT_ADMIN_QUERY)) {
            statement.setString(1, username);
            statement.setString(2, password); // Adăugați această linie pentru a seta parola
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    private boolean checkIfUserExists(String username) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT username FROM administrators WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*private void createAdminTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_TABLE_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}