package boardgame.model;

/**
 * This record stores the row and column number of a Position.
 * It also contains a moveTo() and a toString() method.
 * @param row new {@code Position}'s row.
 * @param col new {@code Position}'s column.
 */
public record Position(int row, int col) {

    /**
     * Moves the current {@code Position} to the given {@code Direction}.
     * @param direction the given {@code Direction} to move to.
     * @return the new {@code Position} after moving it.
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * This function formats a {@code String} from the current {@code Position}.
     * @return the formed {@code String}.
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}