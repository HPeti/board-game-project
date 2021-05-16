package boardgame.jdbi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlayerInfo {
    private String name;
    private int winCount;
    private int loseCount;
}
