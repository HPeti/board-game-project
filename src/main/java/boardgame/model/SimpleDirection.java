package boardgame.model;


/**
 * This enum contains which {@code Direction}'s are available.
 */
public enum SimpleDirection implements Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    /**
     * Stores the row changes.
     */
    private final int rowChange;

    /**
     * Stores the column changes.
     */
    private final int colChange;

    /**
     * Default constructor to {@code SimpleDirection}.
     * @param rowChange current row change.
     * @param colChange current column change.
     */
    SimpleDirection(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Get the current row change of {@code SimpleDirection}.
     * @return row change in integer.
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Get the current column change of {@code SimpleDirection}.
     * @return column change in integer.
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * Determines which enum can be used with the given changes.
     * @param rowChange given row change.
     * @param colChange given row change.
     * @return {@code SimpleDirection} which can be applied instead.
     */
    public static SimpleDirection of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}