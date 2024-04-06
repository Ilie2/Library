package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import bilioteca.Carte;
import bilioteca.Comanda;

public class ComandaTest {
    private Comanda comanda;
    private Carte carte;

    @Before
    public void setUp() {
        carte = new Carte("Titlu", "Autor", false, 5, 14);
        comanda = new Comanda(carte, "UtilizatorTest", 14);
    }

    @Test
    public void testConstructor() {
        assertNotNull(comanda);
    }

    @Test
    public void testGetCarte() {
        assertEquals(carte, comanda.getCarte());
    }

    @Test
    public void testGetNumePersoana() {
        assertEquals("UtilizatorTest", comanda.getNumePersoana());
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testGetDataImprumut() {
        Date today = new Date();
        assertEquals(today.getDate(), comanda.getDataimprumut().getDate());
    }

    @SuppressWarnings("deprecation")
	@Test
    public void testGetDataReturnare() {
        Date todayPlus14 = new Date(new Date().getTime() + 14 * 24 * 60 * 60 * 1000);
        assertEquals(todayPlus14.getDate(), comanda.getDatareturnare().getDate());
    }

    @Test
    public void testGetTermenImprumut() {
        assertEquals(14, comanda.getTermenImprumut());
    }
}
