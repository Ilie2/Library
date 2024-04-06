package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bilioteca.Carte;

public class CarteTest {
    private Carte carte;

    @Before
    public void setUp() {
        carte = new Carte("Titlu", "Autor", false, 5, 14);
    }

    @Test
    public void testGetTitlu() {
        assertEquals("Titlu", carte.getTitlu());
    }

    @Test
    public void testSetTitlu() {
        carte.setTitlu("Noul Titlu");
        assertEquals("Noul Titlu", carte.getTitlu());
    }

    @Test
    public void testGetAutor() {
        assertEquals("Autor", carte.getAutor());
    }

    @Test
    public void testSetAutor() {
        carte.setAutor("Noul Autor");
        assertEquals("Noul Autor", carte.getAutor());
    }

    @Test
    public void testGetImprumutat() {
        assertFalse(carte.getImprumutat());
    }

    @Test
    public void testSetImprumutat() {
        carte.setImprumutat(true);
        assertTrue(carte.getImprumutat());
    }

    @Test
    public void testGetStock() {
        assertEquals(5, carte.getStock());
    }

    @Test
    public void testSetStock() {
        carte.setStock(10);
        assertEquals(10, carte.getStock());
    }

    @Test
    public void testGetTermenImprumut() {
        assertEquals(14, carte.getTermenImprumut());
    }

    @Test
    public void testSetTermenImprumut() {
        carte.setTermenImprumut(30);
        assertEquals(30, carte.getTermenImprumut());
    }

    @Test
    public void testImprumutaCarte() {
        carte.imprumutaCarte("UtilizatorTest");
        assertTrue(carte.getImprumutat());
        assertEquals(4, carte.getStock());
    }
}
