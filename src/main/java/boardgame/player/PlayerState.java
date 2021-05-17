package boardgame.player;

public class PlayerState {
    private static String player1Name;
    private static String player2Name;

    private static Player nextPlayer = Player.PLAYER1;

    public static void init(){
        nextPlayer = Player.PLAYER1;
    }

    public static void setPlayerName(Player playerNumber, String name) {
        switch (playerNumber) {
            case PLAYER1 -> player1Name = name;
            case PLAYER2 -> player2Name = name;
        }
    }

    public static String getPlayerName(Player playerNumber) {
        return switch (playerNumber) {
            case PLAYER1 -> player1Name;
            case PLAYER2 -> player2Name;
        };
    }

    public static String getNextPlayerName() {
        return switch (nextPlayer) {
            case PLAYER1 -> player1Name;
            case PLAYER2 -> player2Name;
        };
    }
    public static String getOtherPlayerName(){
        return switch (nextPlayer) {
            case PLAYER1 -> player2Name;
            case PLAYER2 -> player1Name;
        };
    }

    public static Player getNextPlayer() {
        return nextPlayer;
    }

    public static void alterNextPlayer() {
        nextPlayer = nextPlayer.alter();
    }
}
