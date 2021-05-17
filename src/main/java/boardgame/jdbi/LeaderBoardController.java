package boardgame.jdbi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;

public class LeaderBoardController {
    @FXML
    private TextFlow leaderBoardTextFlow;

    @FXML
    private void initialize(){
        LeaderBoardHandler leaderBoardHandler = new LeaderBoardHandler();
        ArrayList<PlayerInfo> playerInfos = leaderBoardHandler.getFirstTenPlayerInfos();

        int counter = 1;
        for (var playerInfo : playerInfos){
            Logger.debug(playerInfo);
            Text row = new Text(counter + ".\t"+ playerInfo.getName() + ".\tWins: "+ playerInfo.getWinCount() + "\tLoses: " + playerInfo.getLoseCount() + "\n");
            leaderBoardTextFlow.getChildren().add(row);
            counter++;
        }
    }

    @FXML
    private void handleBackToMainMenu(ActionEvent event) throws IOException {
        Logger.debug("Moving back to main menu...");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
