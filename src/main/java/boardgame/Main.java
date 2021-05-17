package boardgame;

import boardgame.mainmenu.MainMenuApplication;
import javafx.application.Application;

/**
 * This class is the Main class of the application.
 */
public class Main {
    /**
     * This launches the {@code MainMenuApplication} from the start of the program.
     * @param args currently not used.
     */
    public static void main(String[] args) {
        Application.launch(MainMenuApplication.class, args);
    }
}