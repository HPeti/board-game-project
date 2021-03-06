// CHECKSTYLE:OFF
package boardgame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import boardgame.jdbi.LeaderBoardHandler;
import boardgame.player.PlayerState;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import boardgame.model.BoardGameModel;
import boardgame.model.SimpleDirection;
import boardgame.model.Position;

import javafx.stage.Stage;
import org.tinylog.Logger;

public class BoardGameController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private BoardGameModel model;

    @FXML
    private GridPane board;

    @FXML
    private Button goToMainMenuButton;

    @FXML
    private Button goToLeaderBoardButton;



    @FXML
    private void initialize() {
        goToLeaderBoardButton.setDisable(true);
        goToMainMenuButton.setDisable(true);
        model = new BoardGameModel();
        selectionPhase = SelectionPhase.SELECT_FROM;
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
    }

    @FXML
    private void handleGameReset(ActionEvent event) {
        Logger.debug("Resetting current game...");
        board.getChildren().clear();
        initialize();
    }

    @FXML
    private void handleAppReset(ActionEvent event) throws IOException {
        Logger.debug("Resetting whole application...(going back to main menu too)");
        board.getChildren().clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleMainMenuButton(ActionEvent event) throws IOException {
        Logger.info("Going back to main menu after a game...");
        board.getChildren().clear();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleLeaderBoardButton(ActionEvent event) throws IOException {
        board.getChildren().clear();
        Logger.debug("Moving to Leaderboard...");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LeaderBoard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void handleGameOver(){
        Logger.info("Game is over");
        alertWinner();
        goToLeaderBoardButton.setDisable(false);
        goToMainMenuButton.setDisable(false);
        LeaderBoardHandler.updateWins(PlayerState.getNextPlayerName());
        LeaderBoardHandler.updateLoses(PlayerState.getOtherPlayerName());
    }
    private void alertWinner(){
        Logger.info("Game won by "+ PlayerState.getNextPlayerName());
        Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
        gameOverAlert.setHeaderText("Game over!");
        gameOverAlert.setContentText("Game won by "+ PlayerState.getNextPlayerName() +"!");
        gameOverAlert.showAndWait();
    }


    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(40); // sug??r
        piece.setFill(color);
        return piece;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        if(!model.isGameOver()) {
            var square = (StackPane) event.getSource();
            var row = GridPane.getRowIndex(square);
            var col = GridPane.getColumnIndex(square);
            var position = new Position(row, col);
            Logger.debug("Click on square {}", position);
            handleClickOnSquare(position);
        }
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected).getAsInt();
                    var direction = SimpleDirection.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        if(!model.isGameOver()){
            setSelectablePositions();
            showSelectablePositions();
        }
        else{
            handleGameOver();
        }
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected).getAsInt();
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }
}
