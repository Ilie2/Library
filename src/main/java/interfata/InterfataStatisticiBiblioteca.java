package interfata;


import javax.swing.*;

import bilioteca.DatabaseConnection;

import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterfataStatisticiBiblioteca extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextArea textArea;

    public InterfataStatisticiBiblioteca() {
        setTitle("Statistici Biblioteca");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JButton btnCeleMaiImprumutateCarti = new JButton("Cele Mai Imprumutate Carti");
        JButton btnTopUtilizatori = new JButton("Top Utilizatori");
        JButton btnDurataMedieImprumuturi = new JButton("Durata Medie Imprumuturi");
        JButton btnTopCartiPeLuna = new JButton("Top Carti Pe Luna");
        textArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(textArea);

        btnCeleMaiImprumutateCarti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afiseazaCeleMaiImprumutateCarti();
            }
        });

        btnTopUtilizatori.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afiseazaTopUtilizatori();
            }
        });

        btnDurataMedieImprumuturi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afiseazaDurataMedieImprumuturi();
            }
        });

        btnTopCartiPeLuna.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afiseazaTopCartiPeLuna();
            }
        });

        panel.add(btnCeleMaiImprumutateCarti);
        panel.add(btnTopUtilizatori);
        panel.add(btnDurataMedieImprumuturi);
        panel.add(btnTopCartiPeLuna);
        panel.add(scrollPane);
        add(panel);
    }

    private void afiseazaCeleMaiImprumutateCarti() {
        try {
            // Obținerea conexiunii folosind clasa DatabaseConnection
            Connection connection = DatabaseConnection.getConnection();

            // Interogare pentru a obține cele mai împrumutate cărți
            String sql = "SELECT titlu_carte, COUNT(*) AS numar_imprumuturi FROM comenzi GROUP BY titlu_carte ORDER BY numar_imprumuturi DESC";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Lista pentru a stoca cele mai împrumutate cărți
            List<String> carti = new ArrayList<>();

            // Afisarea rezultatelor
            textArea.setText("");
            while (resultSet.next()) {
                String titlu = resultSet.getString("titlu_carte");
                int numarImprumuturi = resultSet.getInt("numar_imprumuturi");
                carti.add("Titlu: " + titlu + ", Numar imprumuturi: " + numarImprumuturi);
            }

            // Afisarea primelor 5 cele mai împrumutate cărți
            int limit = Math.min(5, carti.size());
            for (int i = 0; i < limit; i++) {
                textArea.append(carti.get(i) + "\n");
            }

            // Închiderea conexiunii și a resurselor
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void afiseazaTopUtilizatori() {
        try {
            // Obținerea conexiunii folosind clasa DatabaseConnection
            Connection connection = DatabaseConnection.getConnection();

            // Interogare pentru a obține topul utilizatorilor
            String sql = "SELECT nume_persoana, COUNT(*) AS numar_imprumuturi FROM comenzi GROUP BY nume_persoana ORDER BY numar_imprumuturi DESC";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Afisarea rezultatelor
            textArea.setText("");
            while (resultSet.next()) {
                String numeUtilizator = resultSet.getString("nume_persoana");
                int numarImprumuturi = resultSet.getInt("numar_imprumuturi");
                textArea.append("Utilizator: " + numeUtilizator + ", Numar imprumuturi: " + numarImprumuturi + "\n");
            }

            // Închiderea conexiunii și a resurselor
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void afiseazaDurataMedieImprumuturi() {
        try {
            // Obținerea conexiunii folosind clasa DatabaseConnection
            Connection connection = DatabaseConnection.getConnection();

            // Interogare pentru a obține durata medie a împrumuturilor
            String sql = "SELECT AVG((CAST((data_returnare - data_imprumut) AS DOUBLE) / (1000 * 60 * 60 * 24))) AS durata_medie FROM comenzi";
            //String sql = "SELECT AVG(DATEDIFF('DAY', data_imprumut, data_returnare)) AS durata_medie FROM comenzi";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            // Afisarea rezultatului
            if (resultSet.next()) {
                long durataMedie = resultSet.getLong("durata_medie");
                textArea.setText("Durata medie a împrumuturilor: " + durataMedie + " zile.");
            }

            // Închiderea conexiunii și a resurselor
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void afiseazaTopCartiPeLuna() {
        try {
            // Obținerea conexiunii folosind clasa DatabaseConnection
            Connection connection = DatabaseConnection.getConnection();

            // Introducerea lunii și anului utilizatorului
            int luna = Integer.parseInt(JOptionPane.showInputDialog("Introduceți luna (1-12): "));
            int an = Integer.parseInt(JOptionPane.showInputDialog("Introduceți anul: "));

            // Interogare pentru a obține topul cărților împrumutate într-o anumită lună și an
            String sql = "SELECT titlu_carte, COUNT(*) AS numar_imprumuturi FROM comenzi WHERE MONTH(data_imprumut) = ? AND YEAR(data_imprumut) = ? GROUP BY titlu_carte ORDER BY numar_imprumuturi DESC";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, luna);
            statement.setInt(2, an);
            ResultSet resultSet = statement.executeQuery();

            // Afisarea rezultatelor
            textArea.setText("");
            while (resultSet.next()) {
                String titluCarte = resultSet.getString("titlu_carte");
                int numarImprumuturi = resultSet.getInt("numar_imprumuturi");
                textArea.append("Titlu: " + titluCarte + ", Numar imprumuturi: " + numarImprumuturi + "\n");
            }

            // Închiderea conexiunii și a resurselor
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Crearea și afișarea GUI-ului
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InterfataStatisticiBiblioteca().setVisible(true);
            }
        });
    }
}
