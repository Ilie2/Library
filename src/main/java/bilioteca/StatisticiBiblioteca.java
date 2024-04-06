package bilioteca;

import java.util.*;
/**
 * Clasa StatisticiBiblioteca furnizează metode pentru generarea diferitelor statistici
 * legate de activitatea dintr-o bibliotecă, precum cele mai împrumutate cărți, topul utilizatorilor,
 * durata medie a împrumuturilor și topul cărților împrumutate într-o anumită lună și an.
 */
public class StatisticiBiblioteca {
    private List<Comanda> listaComenzi;
    /**
     * Constructor pentru clasa StatisticiBiblioteca.
     *
     * @param listaComenzi Lista de comenzi pentru care se generează statistici.
     */
    public StatisticiBiblioteca(List<Comanda> listaComenzi) {
        this.listaComenzi = listaComenzi;
    }
    /**
     * Afisează cele mai împrumutate cărți, în ordine descrescătoare după numărul de împrumuturi.
     */
    public void afiseazaCeleMaiImprumutateCarti() {
       
        Collections.sort(listaComenzi, Comparator.comparingInt(c -> -Collections.frequency(listaComenzi, c)));

        int limit = Math.min(5, listaComenzi.size());
        for (int i = 0; i < limit; i++) {
            Comanda comanda = listaComenzi.get(i);
            Carte carte = comanda.getCarte();
            System.out.println("Titlu: " + carte.getTitlu() + ", Autor: " + carte.getAutor() +
                    ", Numar imprumuturi: " + Collections.frequency(listaComenzi, comanda));
        }
    }
    /**
     * Afisează topul utilizatorilor, în ordine descrescătoare după numărul de împrumuturi.
     */
    public void afiseazaTopUtilizatori() {
      
        Map<String, Integer> utilizatori = new HashMap<>();
        for (Comanda comanda : listaComenzi) {
            utilizatori.put(comanda.getNumePersoana(), utilizatori.getOrDefault(comanda.getNumePersoana(), 0) + 1);
        }


        List<Map.Entry<String, Integer>> sortedUtilizatori = new ArrayList<>(utilizatori.entrySet());
        sortedUtilizatori.sort((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()));

        int limit = Math.min(3, sortedUtilizatori.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = sortedUtilizatori.get(i);
            System.out.println("Utilizator: " + entry.getKey() + ", Numar imprumuturi: " + entry.getValue());
        }
    }
    /**
     * Afisează durata medie a împrumuturilor în zile.
     */
    public void afiseazaDurataMedieImprumuturi() {
        if (!listaComenzi.isEmpty()) {
            long durataTotala = 0;
            for (Comanda comanda : listaComenzi) {
                durataTotala += comanda.getDatareturnare().getTime() - comanda.getDataimprumut().getTime();
            }
            long durataMedie = durataTotala / listaComenzi.size() / (24 * 60 * 60 * 1000); // Conversie în zile
            System.out.println("Durata medie a imprumuturilor: " + durataMedie + " zile.");
        } else {
            System.out.println("Nu există împrumuturi înregistrate.");
        }
    }
    /**
     * Afisează topul cărților împrumutate într-o anumită lună și an.
     *
     * @param luna Luna pentru care se generează statisticile.
     * @param an   Anul pentru care se generează statisticile.
     */
    public void afiseazaTopCartiPeLuna(int luna, int an) {
        List<Comanda> comenziPeLuna = new ArrayList<>();
        for (Comanda comanda : listaComenzi) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(comanda.getDataimprumut());
            if (calendar.get(Calendar.MONTH) + 1 == luna && calendar.get(Calendar.YEAR) == an) {
                comenziPeLuna.add(comanda);
            }
        }

        Map<Carte, Integer> cartiImprumutate = new HashMap<>();
        for (Comanda comanda : comenziPeLuna) {
            cartiImprumutate.put(comanda.getCarte(), cartiImprumutate.getOrDefault(comanda.getCarte(), 0) + 1);
        }

        List<Map.Entry<Carte, Integer>> sortedCarti = new ArrayList<>(cartiImprumutate.entrySet());
        sortedCarti.sort((entry1, entry2) -> -entry1.getValue().compareTo(entry2.getValue()));

        int limit = Math.min(3, sortedCarti.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<Carte, Integer> entry = sortedCarti.get(i);
            System.out.println("Titlu: " + entry.getKey().getTitlu() + ", Autor: " + entry.getKey().getAutor() +
                    ", Numar imprumuturi: " + entry.getValue());
        }
    }
    public Object[][] getCeleMaiImprumutateCarti(List<Comanda> comenzi) {
        List<Carte> carti = new ArrayList<>();
        for (Comanda comanda : comenzi) {
            if (!carti.contains(comanda.getCarte())) {
                carti.add(comanda.getCarte());
            }
        }

        Collections.sort(carti, new Comparator<Carte>() {
            @Override
            public int compare(Carte c1, Carte c2) {
                return Integer.compare(Collections.frequency(comenzi, c2), Collections.frequency(comenzi, c1));
            }
        });

        int limit = Math.min(5, carti.size());
        Object[][] data = new Object[limit][3];
        for (int i = 0; i < limit; i++) {
            Carte carte = carti.get(i);
            data[i][0] = carte.getTitlu();
            data[i][1] = carte.getAutor();
            data[i][2] = Collections.frequency(comenzi, carte);
        }
        return data;
    }
    public Object[][] getTopCartiPeLuna(int luna, int an) {
        List<Comanda> comenziPeLuna = new ArrayList<>();
        for (Comanda comanda : listaComenzi) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(comanda.getDataimprumut());
            if (calendar.get(Calendar.MONTH) + 1 == luna && calendar.get(Calendar.YEAR) == an) {
                comenziPeLuna.add(comanda);
            }
        }

        List<Carte> carti = new ArrayList<>();
        for (Comanda comanda : comenziPeLuna) {
            if (!carti.contains(comanda.getCarte())) {
                carti.add(comanda.getCarte());
            }
        }

        Collections.sort(carti, new Comparator<Carte>() {
            @Override
            public int compare(Carte c1, Carte c2) {
                return Integer.compare(Collections.frequency(comenziPeLuna, c2), Collections.frequency(comenziPeLuna, c1));
            }
        });

        int limit = Math.min(5, carti.size());
        Object[][] data = new Object[limit][3];
        for (int i = 0; i < limit; i++) {
            Carte carte = carti.get(i);
            data[i][0] = carte.getTitlu();
            data[i][1] = carte.getAutor();
            data[i][2] = Collections.frequency(comenziPeLuna, carte);
        }
        return data;
    }
    public long getDurataMedieImprumuturi() {
        if (listaComenzi.isEmpty()) {
            return 0; // În cazul în care nu există împrumuturi înregistrate, durata medie este considerată 0.
        }

        long durataTotala = 0;
        for (Comanda comanda : listaComenzi) {
            long durataComanda = comanda.getDatareturnare().getTime() - comanda.getDataimprumut().getTime();
            durataTotala += durataComanda;
        }

        return durataTotala / listaComenzi.size(); // Calculăm media duratelor împrumuturilor
    }
    
    public List<Object[]> getCeleMaiImprumutateCarti() {
        Map<Carte, Integer> mapCartiImprumutate = new HashMap<>();

        // Calculăm numărul de împrumuturi pentru fiecare carte
        for (Comanda comanda : listaComenzi) {
            Carte carte = comanda.getCarte();
            mapCartiImprumutate.put(carte, mapCartiImprumutate.getOrDefault(carte, 0) + 1);
        }

        // Sortăm mapa după numărul de împrumuturi în ordine descrescătoare
        List<Map.Entry<Carte, Integer>> listaSortata = new ArrayList<>(mapCartiImprumutate.entrySet());
        listaSortata.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Creăm o listă de obiecte pentru a stoca rezultatele
        List<Object[]> rezultate = new ArrayList<>();

        // Adăugăm primele 5 cărți împrumutate în listă
        int limit = Math.min(5, listaSortata.size());
        for (int i = 0; i < limit; i++) {
            Map.Entry<Carte, Integer> entry = listaSortata.get(i);
            Carte carte = entry.getKey();
            Object[] detaliiCarte = {carte.getTitlu(), carte.getAutor(), entry.getValue()};
            rezultate.add(detaliiCarte);
        }

        return rezultate;
    }
    public Object[][] getTopUtilizatori() {
        Map<String, Integer> mapUtilizatori = new HashMap<>();

        // Calculăm numărul de împrumuturi pentru fiecare utilizator
        for (Comanda comanda : listaComenzi) {
            String numeUtilizator = comanda.getNumePersoana();
            mapUtilizatori.put(numeUtilizator, mapUtilizatori.getOrDefault(numeUtilizator, 0) + 1);
        }

        // Sortăm mapa după numărul de împrumuturi în ordine descrescătoare
        List<Map.Entry<String, Integer>> listaSortata = new ArrayList<>(mapUtilizatori.entrySet());
        listaSortata.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Creăm un array bidimensional pentru a stoca rezultatele
        int limit = Math.min(3, listaSortata.size());
        Object[][] rezultate = new Object[limit][2];

        // Adăugăm topul utilizatorilor în array
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> entry = listaSortata.get(i);
            rezultate[i][0] = entry.getKey(); // Numele utilizatorului
            rezultate[i][1] = entry.getValue(); // Numărul de împrumuturi
        }

        return rezultate;
    }
}
