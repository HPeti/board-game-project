package boardgame.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class is stores information about a {@code Piece}.
 */
public class Piece {

    /**
     * This stores which {@code PieceType} this {@code Piece} represents.
     */
    private final PieceType type;

    /**
     * This stores what {@code Position} this {@code Piece} is at.
     */
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     * Gets the type of the current {@code Piece}.
     * @return the current {@code Piece}'s {@code PieceType}.
     */
    public PieceType getType() {
        return type;
    }

    /**
     * This function is for getting the {@code Piece}'s {@code Position}.
     * @return Current {@code Piece}'s {@code Position}.
     */
    public Position getPosition() {
        return position.get();
    }

    /**
     * This method moves the {@code Piece}'s {@code Position} in the given {@code Direction}
     * @param direction the given Direction to move to.
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }


    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * Returns the current {@code Piece}'s 
     * @return a string with the {@code PieceType} and {@code Position} info.
     */
    public String toString() {
        return type.toString() + position.get().toString();
    }

    public static void main(String[] args) {
        Piece piece = new Piece(PieceType.BLUE, new Position(0, 0));
        piece.positionProperty().addListener((observableValue, oldPosition, newPosition) -> {
            System.out.printf("%s -> %s\n", oldPosition.toString(), newPosition.toString());
        });
        System.out.println(piece);
        piece.moveTo(SimpleDirection.DOWN);
        System.out.println(piece);
    }
}