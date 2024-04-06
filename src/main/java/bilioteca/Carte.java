package bilioteca;

/**
 * Clasa Carte care conține atributele unei cărți.
 */
public class Carte {
    // Atributele cărții
    private String titlu;
    private String autor;
    private Boolean imprumutat;
    private int stock;
    private int termenImprumut;

    /**
     * Constructor pentru clasa Carte.
     *
     * @param titlu         Titlul cărții.
     * @param autor         Autorul cărții.
     * @param imprumutat    Starea de împrumut a cărții.
     * @param stock         Stocul cărții.
     * @param termenImprumut Termenul de împrumut al cărții în zile.
     */
    public Carte(String titlu, String autor, Boolean imprumutat, int stock, int termenImprumut) {
        this.setTitlu(titlu);
        this.setAutor(autor);
        this.setImprumutat(imprumutat);
        this.setStock(stock);
        this.termenImprumut = termenImprumut;
    }

    /**
     * Împrumută cartea și creează o comandă în bibliotecă.
     *
     * @param numePersoana Numele persoanei care împrumută cartea.
     */
    public void imprumutaCarte(String numePersoana) {
        if (!this.imprumutat && this.stock > 0) {
            this.imprumutat = true;
            this.stock--;
            Comanda comanda = new Comanda(this, numePersoana, this.termenImprumut);
            GestBiblioteca.adaugaComanda(comanda);
            System.out.println("Cartea '" + this.titlu + "' a fost împrumutată cu succes.");
        } else {
            System.out.println("Cartea '" + this.titlu + "' nu este disponibilă pentru împrumut.");
        }
    }

    /**
     * Returnează titlul cărții.
     *
     * @return Titlul cărții.
     */
    public String getTitlu() {
        return titlu;
    }

    /**
     * Setează titlul cărții.
     *
     * @param titlu Noul titlu al cărții.
     */
    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    /**
     * Returnează autorul cărții.
     *
     * @return Autorul cărții.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Setează autorul cărții.
     *
     * @param autor Noul autor al cărții.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Returnează starea de împrumut a cărții.
     *
     * @return Starea de împrumut a cărții.
     */
    public Boolean getImprumutat() {
        return imprumutat;
    }

    /**
     * Setează starea de împrumut a cărții.
     *
     * @param imprumutat Noua stare de împrumut a cărții.
     */
    public void setImprumutat(Boolean imprumutat) {
        this.imprumutat = imprumutat;
    }

    /**
     * Returnează stocul cărții.
     *
     * @return Stocul cărții.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setează stocul cărții.
     *
     * @param stock Noul stoc al cărții.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returnează o reprezentare textuală a cărții.
     *
     * @return Șirul de caractere reprezentând informațiile cărții.
     */
    @Override
    public String toString() {
        return "Titlu=" + getTitlu() + ", Autor=" + getAutor() + ", Imprumutat=" + getImprumutat() + ", Stoc=" + getStock();
    }

    /**
     * Returnează termenul de împrumut al cărții.
     *
     * @return Termenul de împrumut al cărții în zile.
     */
    public int getTermenImprumut() {
        return termenImprumut;
    }

    /**
     * Setează termenul de împrumut al cărții.
     *
     * @param termenImprumut Noul termen de împrumut al cărții în zile.
     */
    public void setTermenImprumut(int termenImprumut) {
        this.termenImprumut = termenImprumut;
    }
}
