package samegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Game implements Runnable {

    Mappa mappa;
    GameAi ai;
    public Game(int i, int j) {
        this.mappa = new Mappa(i, j);
        this.ai = new GameAi();
    }

    @SuppressWarnings("resource")
    @Override
    public void run() {
        List<Integer> max = new ArrayList<>();
        while (!this.mappa.fineGioco()) {

            for (int riga = 0; riga < this.mappa.getRighe(); riga++) {
                for (int colonna = 0; colonna < this.mappa.getColonne(); colonna++) {
                    int numeroAdiacenti = this.mappa.getTuttiAdiacenti2(this.mappa.getAdiacenti(riga, colonna)).size();
                    max.add(Integer.valueOf(numeroAdiacenti));
                    Cella cella = new Cella(riga, colonna, numeroAdiacenti);
                    this.ai.addFacts(cella);
                }
            }

            Integer massimo = max
                                 .stream()
                                 .mapToInt(v -> v)
                                 .max()
                                 .orElseThrow(NoSuchElementException::new);

            Max mass = new Max(massimo);
            this.ai.addFacts(mass);

            this.mappa.stampaMappa();
            this.ai.loadFacts();
            Risultato answerSets = this.ai.getAnswerSets();
            System.out.println(answerSets);
            if (answerSets.getI() != null && answerSets.getJ() != null) {

                System.out.println("seleziono la cella " + answerSets);
                this.mappa.rimuoviBlocco(answerSets.getI(), answerSets.getJ());
                this.mappa.stampaCelleSelezionate(answerSets.getI(), answerSets.getJ());
                // this.mappa.stampaMappa();
                // try {
                // Thread.sleep(1000);
                // }
                // catch (InterruptedException e) {
                // e.printStackTrace();
                // }
            }
            else {
                // System.out.println(this.mappa.getTuttiAdiacenti2(this.mappa.getAdiacenti(i, j)));

            }
            this.ai.clear();
            // this.mappa.stampaMappa();
        }

    }

}
