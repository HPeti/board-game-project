package boardgame.jdbi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is made for storing info about a player.
 * This class uses lombok to create constructor for each field.
 * They are used at binding at {@code LeaderBoardDAO} class.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerInfo {
    /**
     * Stores info about Player's name
     */
    private String name;
    /**
     * Stores how many times a Player won.
     */
    private int winCount;
    /**
     * Stores how many times a Player lost.
     */
    private int loseCount;
}
