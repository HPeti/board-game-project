package boardgame.player;

public class PlayerState {
    private String player1Name;
    private String player2Name;

    private Player nextPlayer = Player.PLAYER1;

    public void setPlayerName(Player playerNumber, String name){
        switch (playerNumber){
            case PLAYER1 -> player1Name = name;
            case PLAYER2 -> player2Name = name;
        }
    }
    public String getPlayerName(Player playerNumber){
        return switch (playerNumber){
            case PLAYER1 -> player1Name;
            case PLAYER2 -> player2Name;
        };
    }
    public Player getNextPlayer(){
        return nextPlayer;
    }
    public void alterNextPlayer(){
        nextPlayer = nextPlayer.alter();
    }
}
