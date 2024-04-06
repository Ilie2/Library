package bilioteca;

import java.util.Date;
/**
 * Reprezintă o comandă de bibliotecă pentru împrumutul unei cărți.
 */
public class Comanda {
	private Carte carte;
	private String numePersoana;
	private Date dataimprumut;
	private Date datareturnare;
	/**
     * Construiește un obiect Comanda nou.
     *
     * @param carte          Cartea ce urmează a fi împrumutată.
     * @param numePersoana   Numele persoanei care împrumută cartea.
     * @param termenImprumut Perioada de împrumut în zile.
     */
	public Comanda(Carte carte,String numePersoana,int termenImprumut) {
		this.setCarte(carte);
		this.setNumePersoana(numePersoana);
		this.dataimprumut = new Date();
        this.datareturnare = calculareDataReturnare(termenImprumut);
	}
	/**
     * Calculează data de returnare bazată pe perioada de împrumut.
     *
     * @param termenImprumut Perioada de împrumut în zile.
     * @return Data calculată de returnare.
     */
	@SuppressWarnings({ "deprecation" })
	private Date calculareDataReturnare(int termenImprumut) {
        Date dataReturnare = new Date();
        dataReturnare.setDate(dataReturnare.getDate() + termenImprumut);
        return dataReturnare;
	}
	/**
     * Obține cartea asociată acestei comenzi.
     *
     * @return Cartea asociată acestei comenzi.
     */
	public Carte getCarte() {
		return carte;
	}
	/**
     * Obține numele persoanei care a împrumutat cartea.
     *
     * @return Numele persoanei care a împrumutat cartea.
     */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	 /**
     * Setează numele persoanei care a împrumutat cartea.
     *
     * @param numePersoana Numele persoanei care a împrumutat cartea.
     */
	public String getNumePersoana() {
		return numePersoana;
	}
	/**
     * Obține data când cartea a fost împrumutată.
     *
     * @return Data când cartea a fost împrumutată.
     */
	public void setNumePersoana(String numePersoana) {
		this.numePersoana = numePersoana;
	}
	/**
     * Setează data când cartea a fost împrumutată.
     *
     * @param dataImprumut Data când cartea a fost împrumutată.
     */
	public Date getDataimprumut() {
		return dataimprumut;
	}
	/**
     * Setează data când cartea a fost împrumutată.
     *
     * @param dataImprumut Data când cartea a fost împrumutată.
     */
	public void setDataimprumut(Date dataimprumut) {
		this.dataimprumut = dataimprumut;
	}
	/**
     * Obține data când cartea trebuie returnată.
     *
     * @return Data când cartea trebuie returnată.
     */
	public Date getDatareturnare() {
		return datareturnare;
	}
	/**
     * Setează data când cartea trebuie returnată.
     *
     * @param dataReturnare Data când cartea trebuie returnată.
     */
	public void setDatareturnare(Date datareturnare) {
		this.datareturnare = datareturnare;
	}
	public int getTermenImprumut() {
	    // Assuming datareturnare and dataimprumut are not null
	    long diffInMillies = Math.abs(datareturnare.getTime() - dataimprumut.getTime());
	    long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
	    return (int) diffInDays;
	}

	
}
