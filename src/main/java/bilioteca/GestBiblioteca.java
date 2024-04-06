package bilioteca;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clasa GestBiblioteca gestionează operațiunile legate de bibliotecă, cum ar fi adăugarea, ștergerea,
 * împrumutarea și returnarea cărților, precum și gestionarea comenzilor de împrumut.
 */
@SuppressWarnings("unused")
public class GestBiblioteca {
    private static final String fisier = "biblioteca.csv";
    private static final String imprumuturiFisier = "imprumuturi.csv";
    private static ArrayList<Carte> biblioteca = new ArrayList<>();
    private static ArrayList<Comanda> comenzi = new ArrayList<>();

    static {
        incarca();
        incarcaComenzi();
    }
    public List<Comanda> getListaComenzi() {
        List<Comanda> listaComenzi = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obținerea conexiunii folosind clasa DatabaseConnection
            conn = DatabaseConnection.getConnection();

            // Definirea interogării SQL pentru a selecta toate comenzile
            String sql = "SELECT * FROM comenzi";

            // Crearea declarației pregătite
            stmt = conn.prepareStatement(sql);

            // Executarea interogării și obținerea rezultatelor
            rs = stmt.executeQuery();

            // Parcurgerea rezultatelor și crearea obiectelor Comanda pentru fiecare rând din tabelul comenzi
            while (rs.next()) {
                // Obținerea valorilor din rândul curent
                int id = rs.getInt("id");
                // alte atribute
                // Crearea obiectului Comanda și adăugarea sa în listă
                Comanda comanda = new Comanda(null, sql, id);
                listaComenzi.add(comanda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Închiderea resurselor
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Returnarea listei de comenzi obținută din baza de date
        return listaComenzi;
    }
    /**
     * Adaugă o nouă carte în bibliotecă.
     *
     * @param titlu          Titlul cărții.
     * @param autor          Autorul cărții.
     * @param stock          Stocul disponibil al cărții.
     * @param termenImprumut Termenul de împrumut al cărții în zile.
     */
    /*public static void adauga(String titlu, String autor, int stock, int termenImprumut) {
        biblioteca.add(new Carte(titlu, autor, false, stock, termenImprumut));
        salveaza();
    }*/
    public void adauga(String titlu, String autor, int stock, int termenImprumut) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO carti (titlu, autor, imprumutat, stock, termen_imprumut) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, titlu);
                statement.setString(2, autor);
                statement.setBoolean(3, false); // La adăugare, cartea nu este împrumutată
                statement.setInt(4, stock);
                statement.setInt(5, termenImprumut);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Adaugă o comandă nouă în lista de comenzi.
     *
     * @param comanda Comanda de împrumut.
     */
    public static void adaugaComanda(Comanda comanda) {
        comenzi.add(comanda);
    }

    /**
     * Șterge o carte din bibliotecă după titlu.
     *
     * @param titlu Titlul cărții ce urmează a fi ștearsă.
     */
    /*public static void sterge(String titlu) {
        for (Carte carti : biblioteca) {
            if (carti.getTitlu().equalsIgnoreCase(titlu)) {
                biblioteca.remove(carti);
                salveaza();
                break; // Adăugat break pentru a evita ConcurrentModificationException
            }
        }
    }*/
    public void sterge(String titlu) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM carti WHERE titlu = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, titlu);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Împrumută o carte din bibliotecă.
     *
     * @param titlu        Titlul cărții ce urmează a fi împrumutată.
     * @param numePersoana Numele persoanei care împrumută cartea.
     */
    public void imprumutaCarte(String titlu, String numePersoana) {
        Carte carte = gasesteCarteDupaTitlu(titlu);
        if (carte != null && !carte.getImprumutat() && carte.getStock() > 0) {
            carte.setImprumutat(false); // Modificat pentru a marca cartea ca împrumutată
            carte.setStock(carte.getStock() - 1);
            Comanda comanda = new Comanda(carte, numePersoana, carte.getTermenImprumut());
            adaugaComanda(comanda);
            salveaza(); // Salvarea bibliotecii după actualizarea stocului și a împrumutului
            salveazaComenzi(); // Salvarea comenzilor
            System.out.println("Cartea '" + carte.getTitlu() + "' a fost împrumutată cu succes.");
        } else {
            System.out.println("Cartea nu este disponibilă pentru împrumut sau nu a fost găsită în bibliotecă.");
        }
    }

    public static void returnat(String titlu) {
        for (Comanda comanda : comenzi) {
            Carte carte = comanda.getCarte();
            if (carte != null && carte.getTitlu().equalsIgnoreCase(titlu)) {
                if (!carte.getImprumutat()) {
                    System.out.println("Cartea '" + titlu + "' nu este marcată ca împrumutată.");
                } else {
                    carte.setImprumutat(false); // Modificat pentru a marca cartea ca neîmprumutată
                    carte.setStock(carte.getStock() + 1);
                    comenzi.remove(comanda);
                    salveaza(); // Salvarea bibliotecii după actualizarea stocului și a împrumutului
                    salveazaComenzi(); // Salvarea comenzilor
                    System.out.println("Cartea '" + titlu + "' a fost returnată cu succes.");
                }
                return;
            }
        }
        System.out.println("Cartea '" + titlu + "' nu a fost găsită în lista de împrumuturi.");
    }

    /**
     * Returnează lista de cărți din bibliotecă.
     *
     * @return Lista de cărți din bibliotecă.
     */
    public static ArrayList<Carte> getBiblioteca() {
        return biblioteca;
    }

	private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    private static void incarca() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM carti";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String titlu = resultSet.getString("titlu");
                    String autor = resultSet.getString("autor");
                    boolean imprumutat = resultSet.getBoolean("imprumutat");
                    int stock = resultSet.getInt("stock");
                    int termenImprumut = resultSet.getInt("termen_imprumut");

                    Carte carte = new Carte(titlu, autor, imprumutat, stock, termenImprumut);
                    biblioteca.add(carte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void salveaza() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM carti";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.executeUpdate();
            }

            String insertQuery = "INSERT INTO carti (titlu, autor, imprumutat, stock, termen_imprumut) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                for (Carte carte : biblioteca) {
                    insertStatement.setString(1, carte.getTitlu());
                    insertStatement.setString(2, carte.getAutor());
                    insertStatement.setBoolean(3, carte.getImprumutat());
                    insertStatement.setInt(4, carte.getStock());
                    insertStatement.setInt(5, carte.getTermenImprumut());
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void incarcaComenzi() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM comenzi";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Carte carte = gasesteCarteDupaTitlu(resultSet.getString("titlu_carte"));
                    String numePersoana = resultSet.getString("nume_persoana");
                    int termenImprumut = resultSet.getInt("termen_imprumut");

                    Comanda comanda = new Comanda(carte, numePersoana, termenImprumut);
                    comanda.setDataimprumut(resultSet.getDate("data_imprumut"));
                    comanda.setDatareturnare(resultSet.getDate("data_returnare"));
                    comenzi.add(comanda);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void salveazaComenzi() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM comenzi";
            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                deleteStatement.executeUpdate();
            }

            String insertQuery = "INSERT INTO comenzi (titlu_carte, nume_persoana, termen_imprumut, data_imprumut, data_returnare) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                for (Comanda comanda : comenzi) {
                    insertStatement.setString(1, comanda.getCarte().getTitlu());
                    insertStatement.setString(2, comanda.getNumePersoana());
                    insertStatement.setInt(3, comanda.getTermenImprumut());
                    insertStatement.setDate(4, new java.sql.Date(comanda.getDataimprumut().getTime()));
                    insertStatement.setDate(5, new java.sql.Date(comanda.getDatareturnare().getTime()));
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*private static void salveazaComenzi() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(imprumuturiFisier))) {
            for (Comanda comanda : comenzi) {
                String dataImprumut = formatDate(comanda.getDataimprumut());
                String dataReturnare = formatDate(comanda.getDatareturnare());
                writer.write(comanda.getNumePersoana() + "," + comanda.getCarte().getTitlu() + "," + dataImprumut + "," + dataReturnare);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fișierului de împrumuturi: " + e.getMessage());
        }
    }

    private static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * Salvează informațiile despre cărți în fișierul CSV.
     */
    /*
    public static void salveaza() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier))) {
            for (Carte carte : biblioteca) {
                writer.write(carte.getTitlu() + "," + carte.getAutor() + "," + carte.getImprumutat() + "," + carte.getStock() + "," + carte.getTermenImprumut());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea bibliotecii: " + e.getMessage());
        }
    }

    private static void incarca() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fisier))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] split = linie.split(",");
                if (split.length >= 5) {
                    String titlu = split[0];
                    String autor = split[1];
                    boolean imprumutat = Boolean.parseBoolean(split[2]);
                    int stock = Integer.parseInt(split[3]);
                    int termenImprumut = Integer.parseInt(split[4]);
                    biblioteca.add(new Carte(titlu, autor, imprumutat, stock, termenImprumut));
                } else {
                    // Log or handle incomplete lines as needed
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea bibliotecii: " + e.getMessage());
        }
    }

    private static void incarcaComenzi() {
        try (BufferedReader reader = new BufferedReader(new FileReader(imprumuturiFisier))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] split = linie.split(",");
                if (split.length >= 4) {
                    String numePersoana = split[0];
                    String titluCarte = split[1];
                    Date dataImprumut = parseDate(split[2]);
                    Date dataReturnare = parseDate(split[3]);

                    // Gasim cartea dupa titlu
                    Carte carte = gasesteCarteDupaTitlu(titluCarte);

                    // Corectarea creării obiectului Comanda
                    if (carte != null) {
                        Comanda comanda = new Comanda(carte, numePersoana, carte.getTermenImprumut());
                        comanda.setDataimprumut(dataImprumut);
                        comanda.setDatareturnare(dataReturnare);
                        comenzi.add(comanda);
                    }
                } else {
                    // Log or handle incomplete lines as needed
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului de comenzi: " + e.getMessage());
        }
    }
*/
    private static Carte gasesteCarteDupaTitlu(String titlu) {
        for (Carte carte : biblioteca) {
            if (carte.getTitlu().equalsIgnoreCase(titlu)) {
                return carte;
            }
        }
        return null;
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Eroare la conversia datei: " + e.getMessage());
            return null;
        }
    }
}
