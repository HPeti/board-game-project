package boardgame.model;

/**
 * Contains 2 functions if later we would create new Direction types.
 */
public interface Direction {
    /**
     * Gets the current row change.
     * @return current row change, based on definition
     */
    int getRowChange();
    /**
     * Gets the current column change.
     * @return current column change, based on definition
     */
    int getColChange();
}