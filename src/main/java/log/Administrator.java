package log;

/**
 * Clasa Administrator reprezintă entitatea administratorului și conține informații despre
 * numele de utilizator și parola acestuia pentru autentificare.
 */
public class Administrator {
    private String username;
    private String password;

    /**
     * Constructor pentru clasa Administrator.
     *
     * @param username Numele de utilizator al administratorului.
     * @param password Parola asociată cu numele de utilizator al administratorului.
     */
    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returnează o reprezentare de string a obiectului Administrator.
     *
     * @return String care conține numele de utilizator și parola.
     */
    @Override
    public String toString() {
        return "username=" + username + ", password=" + password;
    }

    /**
     * Returnează numele de utilizator al administratorului.
     *
     * @return Numele de utilizator al administratorului.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returnează parola asociată cu numele de utilizator al administratorului.
     *
     * @return Parola administratorului.
     */
    public String getPassword() {
        return password;
    }

	public void setUsername(String string) {
		// TODO Auto-generated method stub
		
	}

	public void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}
}
