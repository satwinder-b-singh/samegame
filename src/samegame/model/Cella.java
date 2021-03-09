package samegame.model;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cella")
public class Cella {

    @Override
    public String toString() {
        return "Cella [i=" + i + ", j=" + j + ", numColoriVicini=" + numColoriVicini + "]";
    }

    @Param(0)
    int i;
    @Param(1)
    int j;
    @Param(2)
    int numColoriVicini;

    public Cella(int i, int j, int c) {
        this.i = i;
        this.j = j;
        this.numColoriVicini = c;
    }

    public int getColore() {
        return this.numColoriVicini;
    }

    public int getNumColoriVicini() {
        return this.numColoriVicini;
    }

    public void setNumColoriVicini(int numColoriVicini) {
        this.numColoriVicini = numColoriVicini;
    }

    public void setColore(int numColorivicini) {
        this.numColoriVicini = numColorivicini;
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
