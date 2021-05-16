package boardgame.model;

import boardgame.player.Player;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.util.*;

public class BoardGameModel {

    public static int BOARD_ROW_SIZE = 5;
    public static int BOARD_COLUMN_SIZE = 4;

    private final Piece[] pieces;


    private ReadOnlyObjectWrapper<Player> nextPlayer = new ReadOnlyObjectWrapper<>();
    private ReadOnlyBooleanWrapper gameOver = new ReadOnlyBooleanWrapper();

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
        this.nextPlayer.set(Player.PLAYER1);
        this.gameOver.set(false);
    }

    public BoardGameModel(Piece... pieces) {
        checkPieces(pieces);
        this.pieces = pieces.clone();
    }

    private void checkPieces(Piece[] pieces) {
        var seen = new HashSet<Position>();
        for (var piece : pieces) {
            if (! isOnBoard(piece.getPosition()) || seen.contains(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
            seen.add(piece.getPosition());
        }
    }

    public int getPieceCount() {
        return pieces.length;
    }

    public PieceType getPieceType(int pieceNumber) {
        return pieces[pieceNumber].getType();
    }

    public Position getPiecePosition(int pieceNumber) {
        return pieces[pieceNumber].getPosition();
    }

    public ObjectProperty<Position> positionProperty(int pieceNumber) {
        return pieces[pieceNumber].positionProperty();
    }

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

    public Set<SimpleDirection> getValidMoves(int pieceNumber) {
        EnumSet<SimpleDirection> validMoves = EnumSet.noneOf(SimpleDirection.class);
        for (var direction : SimpleDirection.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public ReadOnlyBooleanProperty gameOverProperty(){
        return gameOver.getReadOnlyProperty();
    }

    public boolean isGameOver(){
        return gameOver.get();
    }

    public Player getCurrentPlayer(){
        return nextPlayer.get();
    }

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

    public void move(int pieceNumber, SimpleDirection direction) {
        pieces[pieceNumber].moveTo(direction);
        gameOver.set(getWinCondition(PieceType.RED) || getWinCondition(PieceType.BLUE));
        if (!gameOver.get()){
            nextPlayer.set(nextPlayer.get().alter());
        }

    }

    public static boolean isOnBoard(Position position) {
        return 0 <= position.row() && position.row() < BOARD_ROW_SIZE
                && 0 <= position.col() && position.col() < BOARD_COLUMN_SIZE;
    }

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

    public OptionalInt getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

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