package boardgame.player;

/**
 * This enum stores which Players can be used.
 */
public enum Player{
    PLAYER1, PLAYER2;

    /**
     * Alters a given enum to the other value.
     * @return altered {@code Player} enum.
     */
    public Player alter(){
        return switch (this){
            case PLAYER1 -> PLAYER2;
            case PLAYER2 -> PLAYER1;
        };
    }
}