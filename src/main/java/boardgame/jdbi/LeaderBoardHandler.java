package boardgame.jdbi;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

import java.util.ArrayList;

/**
 * This class handles JDBI connections and operations for {@code LeaderBoardController}.
 */
public class LeaderBoardHandler {
    /**
     * This field is used to store the JDBI's state
     */
    private static Jdbi jdbi;

    /**
     * Default constructor for {@code LeaderBoardHandler} class
     * It initializes a connection, and tries to create a table to store data.
     */
    public LeaderBoardHandler() {
        makeConnection();
        tryCreateTable();
    }

    /**
     * Makes JDBI connection.
     * Creates a path to the given path, and installs plugins for later usage.
     */
    public void makeConnection() {
        jdbi = Jdbi.create("jdbc:h2:file:~/.boardgame-2_19/LeaderBoard", "as", "");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
    }

    /**
     * Tries to create a table.
     * If LeaderBoard's table doesn't exists, it creates it.
     * If it does exists, then logs a line.
     */
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

    /**
     * Checks whether JDBI connection is initialized or not.
     * If it's not initialized, then it initializes it.
     */
    public static void checkConnection() {
        Logger.debug("Checking jdbi connection...");
        if (jdbi == null) {
            new LeaderBoardHandler();
            Logger.debug("Created new connection");
        } else {
            Logger.debug("Connection is already up.");
        }
    }

    /**
     * Stores a win for the given Player's name.
     * It checks whether the Player's name exists in the database.
     * If it exists, it will increase {@code winCount} by one.
     * If it does not exists, it will set {@code winCount} to one.
     * @param name is given as an ID in the database.
     */
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

    /**
     * Stores a lose for the given Player's name.
     * It checks whether the Player's name exists in the database.
     * If it exists, it will increase {@code loseCount} by one.
     * If it does not exists, it will set {@code loseCount} to one.
     * @param name is given as an ID in the database.
     */
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

    /**
     * Gets the first 10 {@code PlayerInfo} data from the database.
     * If 10 {@code PlayerInfo} is not stored, then return as many it exists.
     * @return an {@code ArrayList} which stores {@code PlayerInfo}s.
     */
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
