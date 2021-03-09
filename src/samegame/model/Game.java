package samegame.model;

import java.util.List;

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

        while (!this.mappa.fineGioco()) {
            // Scanner in = new Scanner(System.in);
            // int i = in.nextInt();
            // int j = in.nextInt();
            for (int riga = 0; riga < this.mappa.getRighe(); riga++) {
                for (int colonna = 0; colonna < this.mappa.getColonne(); colonna++) {
                    int numeroAdiacenti = this.mappa.getTuttiAdiacenti2(this.mappa.getAdiacenti(riga, colonna)).size();
                    Cella cella = new Cella(riga, colonna, numeroAdiacenti);
                    this.ai.addFacts(cella);
                }
            }
            // this.mappa.rimuoviBlocco(i, j);
            this.mappa.stampaMappa();
            this.ai.loadFacts();
            List<Cella> answerSets = this.ai.getAnswerSets();
            if (answerSets.size() > 0) {
                Cella findFirst = answerSets.get(0);
                System.out.println("seleziono la cella " + findFirst);
                this.mappa.rimuoviBlocco(findFirst.getI(), findFirst.getJ());
                answerSets.clear();
                this.mappa.stampaMappa();
                try {
                    Thread.sleep(6000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                // System.out.println(this.mappa.getTuttiAdiacenti2(this.mappa.getAdiacenti(i, j)));

            }
            this.ai.clear();
            // this.mappa.stampaMappa();
        }

    }

}
