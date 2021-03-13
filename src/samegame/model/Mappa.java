package samegame.model;

import java.util.ArrayList;
import java.util.List;

public class Mappa {

    private int RIGHE;
    private int COLONNE;

    Colore mappa[][];

    public Colore[][] getMappa() {
        return this.mappa;
    }

    public int getRighe() {
        return this.RIGHE;

    }

    public int getColonne() {
        return this.COLONNE;
    }

    public void setMappa(Colore[][] mappa) {
        this.mappa = mappa;
    }

    public Mappa(int i, int j) {
        this.RIGHE = i;
        this.COLONNE = j;
        mappa = new Colore[RIGHE][COLONNE];
        for (int riga = 0; riga < this.RIGHE; riga++) {
            for (int colonna = 0; colonna < COLONNE; colonna++) {
                this.mappa[riga][colonna] = Colore.getRandomColor();
            }
        }
        System.out.println("La mappa di questa partita è: ");
        this.stampaMappa();
        System.out.println("");
    }

    public void stampaMappa() {
        for (int i = 0; i < RIGHE; i++) {
            for (int j = 0; j < COLONNE; j++) {
                System.out.print(this.mappa[i][j].getColore() + " ");
            }
            System.out.println("");
        }

    }

    public void stampaCelleSelezionate(int riga, int colonna) {
        boolean find = false;
        List<Pair> celle = this.getTuttiAdiacenti2(this.getAdiacenti(riga, colonna));
        for (int i = 0; i < RIGHE; i++) {
            for (int j = 0; j < COLONNE; j++) {
                for (Pair c : celle) {
                    if (c.getI() == i && c.getJ() == j) {
                        System.out.print("[" + this.mappa[i][j].getColore() + "] ");
                        find = true;
                    }
                }
                if (!find)
                    System.out.print(" " + this.mappa[i][j].getColore() + "  ");
                find = false;
            }
            System.out.println("");
        }

    }

    public Boolean haAdiacenti(int i, int j) {

        return this.getAdiacenti(i, j).size() > 1;
    }

    public List<Pair> getAdiacenti(int i, int j) {
        List<Pair> adiacenti = new ArrayList<Pair>();
        if (!adiacenti.contains(new Pair(i, j, this.mappa[i][j])))
            adiacenti.add(new Pair(i, j, this.mappa[i][j]));

        if (this.mappa[i][j].equals(Colore.VUOTO))
            return adiacenti;

        if (i > 0 && mappa[i - 1][j].equals(mappa[i][j])
                && !adiacenti.contains(new Pair(i - 1, j, this.mappa[i - 1][j])))// SOPRA
            adiacenti.add(new Pair(i - 1, j, this.mappa[i - 1][j]));

        if (j < COLONNE - 1 && mappa[i][j + 1].equals(mappa[i][j])
                && !adiacenti.contains(new Pair(i, j + 1, this.mappa[i][j + 1])))// DESTRA
            adiacenti.add(new Pair(i, j + 1, this.mappa[i][j + 1]));

        if (i < RIGHE - 1 && mappa[i + 1][j].equals(mappa[i][j])
                && !adiacenti.contains(new Pair(i + 1, j, this.mappa[i + 1][j])))// SOTTO
            adiacenti.add(new Pair(i + 1, j, this.mappa[i + 1][j]));

        if (j > 0 && mappa[i][j - 1].equals(mappa[i][j])
                && !adiacenti.contains(new Pair(i, j - 1, this.mappa[i][j - 1])))// SINISTRA
            adiacenti.add(new Pair(i, j - 1, this.mappa[i][j - 1]));

        return adiacenti;
    }

    public List<Pair> getTuttiAdiacenti2(List<Pair> adiacenti) {

        int size = adiacenti.size();
        List<Pair> copia = new ArrayList<>(adiacenti);
        for (Pair p : copia) {
            List<Pair> adia = getAdiacenti(p.getI(), p.getJ());
            for (Pair b : adia) {
                if (!copia.contains(b))
                    adiacenti.add(b);
            }
        }
        if (adiacenti.size() != size)
            getTuttiAdiacenti2(adiacenti);

        return adiacenti;
    }

    public Boolean fineGioco() {
        for (int i = 0; i < RIGHE; i++) {
            for (int j = 0; j < COLONNE; j++) {

                if (!this.mappa[i][j].equals(Colore.VUOTO) && this.haAdiacenti(i, j))
                    return false;
                Colore colore = this.mappa[i][j];
            }
        }
        if (Colore.VUOTO.equals(0)) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!   COMPLIMENTI HAI VINTO   !!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        }
        else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!   PECCATO HO PERSO      !!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
        this.stampaMappa();
        return true;
    }

    public Boolean rimuoviBlocco(int i, int j) {

        if (!this.haAdiacenti(i, j)) {
            System.out.println("Questa cella non ha adiacenti da rimuovere");
            return false;
        }
        else {
            List<Integer> colonne = new ArrayList<Integer>();
            List<Pair> adiacenti = this.getTuttiAdiacenti2(this.getAdiacenti(i, j));
            adiacenti.stream().forEach(adiacente -> colonne.add(adiacente.getJ()));
            adiacenti.stream().forEach(adiacente -> mappa[adiacente.getI()][adiacente.getJ()] = Colore.VUOTO);

            colonne.stream().forEach(colonna -> this.scalaColonna(colonna));
            System.out.println("Ho rimosso il blocco selezionato");
            return true;
        }
    }

    private void scalaColonna(Integer colonna) {
        for (int i = 0; i < COLONNE; i++) {
            if (this.mappa[i][colonna].equals(Colore.VUOTO)) {
                for (int k = i; k > 0; k--) {
                    this.mappa[k][colonna] = this.mappa[k - 1][colonna];
                }
                this.mappa[0][colonna] = Colore.VUOTO;
            }
        }
    }

}
