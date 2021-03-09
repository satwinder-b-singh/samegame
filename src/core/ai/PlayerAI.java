package core.ai;

import java.io.IOException;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;
import samegame.model.Colore;
import samegame.model.Mappa;
import samegame.model.Pair;

public class PlayerAI {

    Mappa mappa;
    private static Handler handler;
    private ASPInputProgram input;
    private String path;
    private String dlv_input;
    public PlayerAI() throws IOException {
        path = "./dlv2.exe";
        dlv_input = "./input_player.dlv";
        handler = new DesktopHandler(new DLV2DesktopService(path));
        input = new ASPInputProgram();
        input.addFilesPath(dlv_input);
    }

    public void setMappa(Mappa mappa) {
        this.mappa = mappa;
    }

    public Mappa getMappa() {
        return this.mappa;
    }

    public void clear() {
        handler.removeAll();
        input = new ASPInputProgram();
        input.addFilesPath(dlv_input);
    }

    public void load_fact() throws Exception {
        try {
            ASPMapper.getInstance().registerClass(Pair.class);
        }
        catch (ObjectNotValidException | IllegalAnnotationException e1) {
            e1.printStackTrace();
        }
        InputProgram facts = new ASPInputProgram();
        for (int i = 0; i < this.mappa.getRighe(); i++) {
            for (int j = 0; j < this.mappa.getColonne(); j++) {
                if (this.mappa.getMappa()[i][j] != Colore.VUOTO) {
                    try {
                        facts.addObjectInput(new Pair(i, j, this.mappa.getMappa()[i][j]));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        // input.addObjectInput(pos);
        // for (Action action : actions)
        // input.addObjectInput(action);
        // for (Distance dist : dists)
        // input.addObjectInput(dist);

        handler.addProgram(input);

    }

    public AnswerSets getAnswerSets() throws Exception {
        Output o = handler.startSync();
        if (((AnswerSets) o).getAnswersets().size() == 0) {
            throw new Exception("NO ANSWERSET!");
        }
        return (AnswerSets) o;

    }
}
