package boardgame.player;

public enum Player{
    PLAYER1, PLAYER2;

    public Player alter(){
        return switch (this){
            case PLAYER1 -> PLAYER2;
            case PLAYER2 -> PLAYER1;
        };
    }
}