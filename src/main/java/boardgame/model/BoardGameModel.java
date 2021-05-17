package boardgame.model;

import boardgame.player.Player;
import boardgame.player.PlayerState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.*;

/**
 * This class stores infos about the current game's model.
 * Stores infos such as board size, pieces, moves, valid moves, etc...
 */
public class BoardGameModel {

    /**
     * Defines the row size of the board.
     */
    private static int BOARD_ROW_SIZE = 5;
    /**
     * Defines the column size of the board.
     */
    private static int BOARD_COLUMN_SIZE = 4;

    /**
     * This array stores the {@code Piece}s on the board.
     */
    private final Piece[] pieces;


    /**
     * Stores the which Player's turn it is. Also it's only readable from the outside.
     */
    private ReadOnlyObjectWrapper<Player> nextPlayer = new ReadOnlyObjectWrapper<>();

    /**
     * Stores the which the game is over or not. Also it's only readable from the outside.
     */
    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();

    /**
     * This is the default start-state of the board, when a new model is created.
     * It adds {@code Piece}s to the board, sets which {@code Player} starts, and the game is not over.
     */
    public BoardGameModel() {
        this(new Piece(PieceType.BLUE, new Position(0, 0)),
                new Piece(PieceType.RED, new Position(0, 1)),
                new Piece(PieceType.BLUE, new Position(0, 2)),
                new Piece(PieceType.RED, new Position(0, BOARD_COLUMN_SIZE - 1)),
                new Piece(PieceType.RED, new Position(BOARD_ROW_SIZE - 1, 0)),
                new Piece(PieceType.BLUE, new Position(BOARD_ROW_SIZE - 1, 1)),
                new Piece(PieceType.RED, new Position(BOARD_ROW_SIZE - 1, 2)),
                new Piece(PieceType.BLUE, new Position(BOARD_ROW_SIZE - 1, BOARD_COLUMN_SIZE - 1))
        );
        PlayerState.init();
        this.nextPlayer.set(PlayerState.getNextPlayer());
        this.gameOver.set(false);
    }

    /**
     * Helper constructor to the main constructor.
     * It check if the given {@code Piece}s {@code Position}'s are correct or not.
     * If it is valid, then adds pieces to board.
     * @param pieces given {@code Piece} collection.
     */
    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    /**
     * Checks if the given {@code Piece} array is valid or not.
     * @param pieces given {@code Piece} array.
     */
    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    /**
     * Gets the piece count on the board.
     * @return number of pieces.
     */
    public int getPieceCount() {
        return pieces.length;
    }

    /**
     * Gets the type of given {@code Piece}'s number.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @return the {@code PieceType} of the given {@code Piece}.
     */
    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    /**
     * Gets the position of given {@code Piece}'s number.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @return the {@code Position} of the given {@code Piece}.
     */
    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    public int getBoardRowSize(){
        return BOARD_ROW_SIZE;
    }

    public int getBoardColumnSize(){
        return BOARD_COLUMN_SIZE;
    }

    /**
     * Returns the positionProperty of the given {@code Piece}'s number.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @return the positionProperty of the given {@code Piece}.
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

    /**
     * Checks if the given {@code Piece} can move to the given {@code SimpleDirection}.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @param direction {@code SimpleDirection}, where we want to move the {@code Piece}'s {@code Position} to.
     * @return if it is a valid move or not.
     */
    public boolean isValidMove(int pieceNumber, SimpleDirection direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (! isOnBoard(newPosition)) {
            return false;
        }
        for (var piece : pieces) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets all valid moves of the given {@code Piece}'s number.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @return all valid {@code SimpleDirection} that can be applied to given {@code Piece}.
     */
    public Set<SimpleDirection> getValidMoves(int pieceNumber) {
        EnumSet<SimpleDirection> validMoves = EnumSet.noneOf(SimpleDirection.class);
        for (var direction : SimpleDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Gets the gameOverProperty.
     * @return the gameOver as a property.
     */
    public ReadOnlyBooleanProperty gameOverProperty(){
        return gameOver.getReadOnlyProperty();
    }

    /**
     * Gets if the game is over or not.
     * @return it the game is over or not.
     */
    public boolean isGameOver(){
        return gameOver.get();
    }

    /**
     * Checks if the board is in win condition for the given {@code PieceType}.
     * @param pieceType the {@code PieceType} that we want to check on.
     * @return if the board is in win condition for the given {@code PieceType}.
     */
    public boolean getWinCondition(PieceType pieceType){
        for (int i = 0; i < 4; i++) {
            int rowCount = 0;
            int colCount = 0;
            for(var piece : pieces){
                if(piece.getType().equals(pieceType)){
                    if(piece.getPosition().row() == i)
                        rowCount++;
                    if(piece.getPosition().col() == i)
                        colCount++;
                }
            }
            if(rowCount == 3 || colCount == 3){
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the given {@code Piece} to the given {@code SimpleDirection}.
     * It also checks if the game is over, so we alternate the next player.
     * If it's not game over, then it alternates the nextPlayer.
     * @param pieceNumber the number of the {@code Piece} in the pieces array.
     * @param direction the {@code SimpleDirection} where we want to move the {@code Piece} to.
     */
    public void move(int pieceNumber, SimpleDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        gameOver.set(getWinCondition(PieceType.RED) || getWinCondition(PieceType.BLUE));
        if (!gameOver.get()){
            PlayerState.alterNextPlayer();
            nextPlayer.set(PlayerState.getNextPlayer());
        }

    }

    /**
     * Checks if the given {@code Positon} is on the board or not.
     * @param position {@code Positon} that we want to check on.
     * @return if the {@code Positon} is on board or not.
     */
    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_ROW_SIZE
                && 0 <= position.col() && position.col() < BOARD_COLUMN_SIZE;
    }

    /**
     * Gets the movable {@code Piece} to the nextPlayer.
     * @return the {@code Postions}s the nextPlayer can choose.
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>();
        PieceType searchedType = switch (nextPlayer.get()) {
            case PLAYER1 -> PieceType.BLUE;
            case PLAYER2 -> PieceType.RED;
        };
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getType().equals(searchedType))
                if (getValidMoves(i).size() > 0)
                    positions.add(pieces[i].getPosition());
        }
        return positions;
    }

    /**
     * Gets the given {@code Position}'s {@code Piece}'s number.
     * If there is no {@code Piece} at the given {@code Position}, then it returns an empty {@code OptionalInt}.
     * @param position given {@code Position} that we want to check on.
     * @return {@code OptionalInt} which can be empty or contains the {@code Piece}'s number.
     */
    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    /**
     * Creates a {@code String} from the current state of the {@code Piece}'s on the board.
     * @return a {@code String} which can represent the state of {@code Piece}s on the board.
     */
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            joiner.add(piece.toString());
        }
        return joiner.toString();
    }

    public static void main(String[] args) {
        BoardGameModel model = new BoardGameModel();
        System.out.println(model);
    }

}