package interfata;

import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bilioteca.Carte;
import bilioteca.GestBiblioteca;

public class Afiseaza extends javax.swing.JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Creates new form Afiseaza
     */
    public Afiseaza() {
        initComponents();
        afiseazaCarti();
    }
    
    private void initComponents() {
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"Titlu", "Autor", "Imprumutata", "Stoc", "Termen imprumut"}
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }

    private void afiseazaCarti() {
        // Obținerea listei de cărți din bibliotecă
        ArrayList<Carte> carti = GestBiblioteca.getBiblioteca();
        
        // Definirea coloanelor tabelului
        String[] coloane = {"Titlu", "Autor", "Imprumutata", "Stoc", "Termen imprumut"};
        
        // Crearea unui model de tabel
        DefaultTableModel model = new DefaultTableModel(coloane, 0);
        
        // Adăugarea rândurilor în tabel
        for (Carte carte : carti) {
            Object[] rand = {carte.getTitlu(), carte.getAutor(), carte.getImprumutat(), carte.getStock(), carte.getTermenImprumut()};
            model.addRow(rand);
        }
        
        // Setarea modelului de tabel în componenta jTable
        jTable1.setModel(model);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/UIManager.LookAndFeelInfo.html#available */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Afiseaza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Afiseaza().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration                   
}
