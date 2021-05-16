package boardgame.mainmenu;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private void handleLeaderboard(ActionEvent event) {
        Logger.debug("Please implement leaderboard...");
    }
    @FXML
    private void handleNewGame(ActionEvent event) throws IOException {
        Logger.debug("Moving to Player menu...");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/PlayerMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleQuitGame(ActionEvent event){
        Logger.debug("Exiting...");
        Platform.exit();
    }
}
