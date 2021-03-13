package samegame.model;

import samegame.util.ConsoleColors;

public class main {

    public static void main(String[] args) {
        System.out.println(ConsoleColors.RED + "RED COLORED" +
                ConsoleColors.RESET + " NORMAL");
        System.out.println("\033[0m BLABLA \033[0m\n");
        Game g = new Game(10, 10);
        g.run();

    }

}
