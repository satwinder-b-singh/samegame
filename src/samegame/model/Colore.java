package samegame.model;

import java.util.Random;

public enum Colore {

    VUOTO(0),
    ROSSO(1),
    GIALLO(2),
    VERDE(3),
    BLU(4);

    private int colore;
    Colore(int i) {
        this.colore = i;
    }

    public static Colore getRandomColor() {
        Random random = new Random();

        return values()[random.nextInt((4 - 1) + 1) + 1];

    }

    public int getColore() {
        return this.colore;
    }

    public void setColore(int colore) {
        this.colore = colore;
    }
}
