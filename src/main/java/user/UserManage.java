package user;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clasa UserManage gestionează operațiunile de înregistrare și autentificare a utilizatorilor.
 * Aceasta include citirea și scrierea datelor din/intr-un fișier și manipularea listei de utilizatori.
 */
/*public class UserManage {
    private List<User> users;
    private static final String filePath = "file.txt";

    /**
     * Constructor pentru clasa UserManage.
     * Inițializează lista de utilizatori încărcând datele din fișierul specificat.
     */
    /*public UserManage() {
        this.users = loadUsers();
    }*/

    /**
     * Încarcă utilizatorii din fișierul specificat.
     *
     * @return Lista de utilizatori încărcați.
     */
    /*private List<User> loadUsers() {
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 2) {
                    String username = userData[0];
                    String password = userData[1];
                    userList.add(new User(username, password));
                } else {
                    // Poți trata cazul în care linia nu conține datele așteptate
                    // Poate vrei să afișezi un avertisment sau să tratezi altfel situația în funcție de cerințele tale.
                    System.err.println("Avertisment: Linia nu conține datele așteptate: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }

    /**
     * Salvează utilizatorii în fișierul specificat.
     */
    /*private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Înregistrează un nou utilizator cu numele de utilizator și parola specificate.
     *
     * @param username Numele de utilizator pentru noul utilizator.
     * @param password Parola pentru noul utilizator.
     * @return True dacă înregistrarea a fost reușită, false dacă numele de utilizator există deja.
     */
   /* public boolean registerUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return false; // Numele de utilizator există deja
            }
        }

        User newUser = new User(username, password);
        users.add(newUser);
        saveUsers();
        return true;
    }

    /**
     * Autentifică un utilizator cu numele de utilizator și parola specificate.
     *
     * @param username Numele de utilizator al utilizatorului.
     * @param password Parola utilizatorului.
     * @return True dacă autentificarea este reușită, false dacă numele de utilizator sau parola sunt invalide.
     */
   /* public boolean loginUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true; // Autentificare reușită
            }
        }
        return false; // Nume de utilizator sau parolă invalide
    }
}
*/
public class UserManage {

    private static final String DATABASE_URL = "jdbc:derby://localhost:1527/tres;create=true";
    private static final String INSERT_ADMIN_QUERY = "INSERT INTO \"user\" (username, password) VALUES (?, ?)";
    private static final String SELECT_ADMIN_QUERY = "SELECT * FROM \"user\" WHERE username = ? AND password =?";
    private static final String CREATE_ADMIN_TABLE_QUERY = "CREATE TABLE \"user\" (id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, username VARCHAR(50) UNIQUE, password VARCHAR(50))";


    /*public UserManage() {
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
             PreparedStatement statement = connection.prepareStatement("SELECT username FROM \"user\" WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   /* private void createAdminTableIfNotExists() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(CREATE_ADMIN_TABLE_QUERY)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    }
