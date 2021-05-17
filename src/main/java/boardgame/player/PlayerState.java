package boardgame.player;

/**
 * This class stores and manages infos about Player names and nextPlayer.
 */
public class PlayerState {

    /**
     * First {@code Player}'s name.
     */
    private static String player1Name;

    /**
     * Second {@code Player}'s name.
     */
    private static String player2Name;

    /**
     * Defines which {@code Player}'s turn it is now.
     */
    private static Player nextPlayer = Player.PLAYER1;

    /**
     * Initializes the which {@code Player}'s starts first.
     */
    public static void init(){
        nextPlayer = Player.PLAYER1;
    }

    /**
     * Sets the given name to the given {@code Player}.
     * @param playerNumber the enum of the {@code Player}
     * @param name this name will be used to the given {@code Player} from now on.
     */
    public static void setPlayerName(Player playerNumber, String name) {
        switch (playerNumber) {
            case PLAYER1 -> player1Name = name;
            case PLAYER2 -> player2Name = name;
        }
    }

    /**
     * Gets the name to the given {@code Player}.
     * @param playerNumber the enum of the {@code Player}
     * @return the name of the given {@code Player}
     */
    public static String getPlayerName(Player playerNumber) {
        return switch (playerNumber) {
            case PLAYER1 -> player1Name;
            case PLAYER2 -> player2Name;
        };
    }

    /**
     * Gets the name of current {@code Player} whos turn it is.
     * @return the current {@code Player}'s name.
     */
    public static String getNextPlayerName() {
        return switch (nextPlayer) {
            case PLAYER1 -> player1Name;
            case PLAYER2 -> player2Name;
        };
    }
    /**
     * Gets the name of other {@code Player} whos turn it isn't.
     * @return the other {@code Player}'s name.
     */
    public static String getOtherPlayerName(){
        return switch (nextPlayer) {
            case PLAYER1 -> player2Name;
            case PLAYER2 -> player1Name;
        };
    }

    /**
     * Gets the current {@code Player}, whos turn it is.
     * @return the other {@code Player}'s name.
     */
    public static Player getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Alter's which {@code Player}'s turn it is.
     */
    public static void alterNextPlayer() {
        nextPlayer = nextPlayer.alter();
    }
}
