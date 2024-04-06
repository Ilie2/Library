package user;

/**
 * Clasa User reprezintă entitatea utilizatorului și conține informații despre numele de utilizator și parola acestuia.
 */
public class User {
    private String username;
    private String password;

    /**
     * Constructor pentru clasa User.
     *
     * @param username Numele de utilizator al utilizatorului.
     * @param password Parola asociată cu numele de utilizator al utilizatorului.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returnează o reprezentare de string a obiectului User.
     *
     * @return String care conține numele de utilizator și parola.
     */
    @Override
    public String toString() {
        return "username=" + username + ", password=" + password;
    }

    /**
     * Returnează numele de utilizator al utilizatorului.
     *
     * @return Numele de utilizator al utilizatorului.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returnează parola asociată cu numele de utilizator al utilizatorului.
     *
     * @return Parola utilizatorului.
     */
    public String getPassword() {
        return password;
    }
}
