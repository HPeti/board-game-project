package boardgame.player;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class PlayerMenuController {
    @FXML
    private TextField player1Field;

    @FXML
    private TextField player2Field;

    @FXML
    private void initialize() {
        player1Field.setText(System.getProperty("user.name"));
    }

    private void setPlayerNames() {
        if (player1Field.getText().equals("")) {
            Logger.debug("Used default name for Player1");
            PlayerState.setPlayerName(Player.PLAYER1, "DefaultPlayer1Name");
        } else
            PlayerState.setPlayerName(Player.PLAYER1, player1Field.getText());

        if (player2Field.getText().equals("")) {
            Logger.debug("Used default name for Player2");
            PlayerState.setPlayerName(Player.PLAYER2, "DefaultPlayer2Name");
        } else
            PlayerState.setPlayerName(Player.PLAYER2, player2Field.getText());
    }

    @FXML
    private void handleBackToMainMenu(ActionEvent event) throws IOException {
        Logger.debug("Back to Main menu...");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();

    }

    @FXML
    private void handleContinueToNewGame(ActionEvent event) throws IOException {
        Logger.debug("Moving to game controls...");
        setPlayerNames();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/ui.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
