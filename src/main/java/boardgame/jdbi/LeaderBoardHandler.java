package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

public class LeaderBoardHandler {
    private static Jdbi jdbi;

    public LeaderBoardHandler(){
        jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame-2_19/LeaderBoard","as","");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
        try {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {dao.createTable(); return true;});
        }
        catch (Exception exception){
            Logger.debug("Table already exists! ("+exception.getClass()+")");
        }
    }

    public static void updateWins(String name){
        boolean playerExists = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.playerExists(name));
        if (playerExists){
            jdbi.withExtension(LeaderBoardDAO.class, dao ->{ dao.incrementPlayerWins(name); return true;});
        }
        else {
            jdbi.withExtension(LeaderBoardDAO.class, dao ->{ dao.insertWinnerPlayer(name); return true;});
        }
    }
    public static void updateLoses(String name){
        boolean playerExists = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.playerExists(name));
        if (playerExists){
            jdbi.withExtension(LeaderBoardDAO.class, dao ->{ dao.incrementPlayerLoses(name); return true;});
        }
        else {
            jdbi.withExtension(LeaderBoardDAO.class, dao ->{ dao.insertLoserPlayer(name);return true;});
        }
    }
    public static ArrayList<PlayerInfo> getFirstTenPlayerInfos(){
        if (jdbi == null){
            ArrayList<PlayerInfo> topTen = new ArrayList<PlayerInfo>();
            Logger.debug("nincs jdbi");
            return topTen;
        }
        else {
            PlayerInfo[] playerInfos = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.getPlayersOrdered());
            /*
            String[] playerNames = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenName());
            int[] winnerScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenWinCount());
            int[] losesScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenLosesCount());
            int[] drawsScores = jdbi.withExtension(BoardSetDao.class, dao -> dao.getFirstTenDrawsCount());
            */
            int topCount = 10;
            ArrayList<PlayerInfo> topTen = new ArrayList<PlayerInfo>();
            if (playerInfos.length < 10) {
                topCount = playerInfos.length;
            }
            for (int i = 0; i < topCount; i++) {
                topTen.add(playerInfos[i]);
            }
            return topTen;
        }
    }
}
