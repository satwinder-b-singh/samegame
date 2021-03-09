package samegame.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("pair")
public class Pair {

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (colore != other.colore)
            return false;
        if (i != other.i)
            return false;
        if (j != other.j)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Pair [i=" + i + ", j=" + j + ", Colore=" + colore + "]";
    }

    @Param(0)
    int i;
    @Param(1)
    int j;
    @Param(2)
    Colore colore;

    public Pair(int i, int j, Colore c) {
        this.i = i;
        this.j = j;
        this.colore = c;
    }

    public Colore getColore() {
        return this.colore;
    }

    public void setColore(Colore colore) {
        this.colore = colore;
    }

    public int getI() {
        return this.i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return this.j;
    }

    public void setJ(int j) {
        this.j = j;
    }

}
