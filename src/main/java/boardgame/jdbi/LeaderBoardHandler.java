package boardgame.jdbi;

import lombok.extern.java.Log;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

public class LeaderBoardHandler {
    private static Jdbi jdbi;

    public LeaderBoardHandler() {
        makeConnection();
        tryCreateTable();
    }

    public void makeConnection() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame-2_19/LeaderBoard", "as", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
    }

    public void tryCreateTable() {
        try {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {
                dao.createTable();
                return true;
            });
        } catch (Exception exception) {
            Logger.debug("Table already exists! (" + exception.getClass() + ")");
        }
    }

    public static void checkConnection() {
        Logger.debug("Checking jdbi connection...");
        if (jdbi == null) {
            new LeaderBoardHandler();
            Logger.debug("Created new connection");
        } else {
            Logger.debug("Connection is already up.");
        }
    }

    public static void updateWins(String name) {
        checkConnection();
        boolean playerExists = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.playerExists(name));
        if (playerExists) {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {
                dao.incrementPlayerWins(name);
                return true;
            });
        } else {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {
                dao.insertWinnerPlayer(name);
                return true;
            });
        }
    }

    public static void updateLoses(String name) {
        checkConnection();
        boolean playerExists = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.playerExists(name));
        if (playerExists) {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {
                dao.incrementPlayerLoses(name);
                return true;
            });
        } else {
            jdbi.withExtension(LeaderBoardDAO.class, dao -> {
                dao.insertLoserPlayer(name);
                return true;
            });
        }
    }

    public static ArrayList<PlayerInfo> getFirstTenPlayerInfos() {
        checkConnection();
        PlayerInfo[] playerInfos = jdbi.withExtension(LeaderBoardDAO.class, dao -> dao.getPlayersOrdered());
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
