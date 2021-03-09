package samegame.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
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

    public List<Cella> getAnswerSets() {
        List<Cella> max = new ArrayList<Cella>();
        Output o = GameAi.getHandler().startSync();
        AnswerSets answersets = (AnswerSets) o;
        if (answersets.getAnswersets().size() == 0) {
            System.out.println("No answer set");
        }
        else {
            String answerSetsString = answersets.getAnswerSetsString();
            System.out.println("Stampo l'answerset  \n" + answerSetsString);
            Pattern matcher = Pattern.compile(
                    "maxColori\\((?<firstNumber>[0-9]+),[ \t]*(?<secondNumber>[0-9]+),[ \t]*(?<thirdNumber>[0-9]+)\\)");
            Matcher m = matcher.matcher(answerSetsString);

            while (m.find()) {
                max.add(new Cella(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3))));
            }
        }
        return max;
    }

    public void addFacts(Cella c) {
        try {
            this.facts.addObjectInput(c);
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
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