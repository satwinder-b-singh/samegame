package samegame.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GameAi {

    private static String inputResource = "input/file";
    private static Handler handler;
    InputProgram encoding;
    InputProgram facts;
    public GameAi() {
        handler = new DesktopHandler(new DLV2DesktopService("./dlv2.exe"));
        facts = new ASPInputProgram();
        encoding = new ASPInputProgram();
        encoding.addFilesPath(inputResource);
    }

    public void clear() {
        this.facts.clearAll();
        handler.removeAll();

    }

    public void loadFacts() {
        GameAi.handler.addProgram(this.facts);
        GameAi.handler.addProgram(this.encoding);
    }

    public Risultato getAnswerSets() {
        Risultato risultato = null;
        Output o = GameAi.getHandler().startSync();
        AnswerSets answersets = (AnswerSets) o;
        while (answersets.getAnswersets().get(0).getAnswerSet().isEmpty()) {
            o = GameAi.getHandler().startSync();
            answersets = (AnswerSets) o;
        }
        if (answersets.getAnswersets().size() == 0) {
            System.out.println("No answer set");
        }
        else {

            String answerSetsString = answersets.getAnswerSetsString();
            Pattern pattern = Pattern.compile("^scelgo\\((\\d+),(\\d+)\\)");

            for (AnswerSet an : answersets.getAnswersets()) {
                Matcher matcher;
                for (String atom : an.getAnswerSet()) {
                    matcher = pattern.matcher(atom);
                    if (matcher.find()) {
                        Integer i = Integer.valueOf(matcher.group(1));
                        Integer j = Integer.valueOf(matcher.group(2));
                        risultato = new Risultato(i, j);
                    }
                }
            }
        }
        return risultato;
    }

    public void addFacts(Cella c) {
        try {
            this.facts.addObjectInput(c);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addFacts(Max max) {
        try {
            this.facts.addObjectInput(max);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Handler getHandler() {
        return GameAi.handler;
    }

    public static void setHandler(Handler handler) {
        GameAi.handler = handler;
    }

    public InputProgram getFacts() {
        return this.facts;
    }

    public void setFacts(InputProgram facts) {
        this.facts = facts;
    }
}