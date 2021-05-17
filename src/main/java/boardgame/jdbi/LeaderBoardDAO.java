package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * This interface contains info about handling database.
 * It contains basic operations.
 */
@RegisterBeanMapper(PlayerInfo.class)
public interface LeaderBoardDAO {

    /**
     * Creates a LeaderBoard table with this operation.
     */
    @SqlUpdate("""
            create table LeaderBoard(
                name varchar2 primary key,
                winCount int not null,
                loseCount int not null)
            """)
    void createTable();

    /**
     * Insert a new Player with a win into the database.
     * @param name given Player's name
     */
    @SqlUpdate("insert into LeaderBoard values(:name, 1, 0)")
    void insertWinnerPlayer(@Bind("name") String name);

    /**
     * Insert a new Player with a lose into the database.
     * @param name given Player's name
     */
    @SqlUpdate("insert into LeaderBoard values(:name, 0, 1)")
    void insertLoserPlayer(@Bind("name") String name);

    /**
     * Updates a Player's winCount and increment it by one in the database.
     * @param name given Player's name
     */
    @SqlUpdate("update LeaderBoard set winCount = winCount + 1 where name = :name ")
    void incrementPlayerWins(@Bind("name") String name);

    /**
     * Updates a Player's loseCount and increment it by one in the database.
     * @param name given Player's name
     */
    @SqlUpdate("update LeaderBoard set loseCount = loseCount + 1 where name = :name ")
    void incrementPlayerLoses(@Bind("name") String name);

    /**
     * Gets how many times the given Player won.
     * @param name given Player's name
     * @return how many times the given Player won.
     */
    @SqlQuery("select winCount from LeaderBoard where name = :name")
    int numberOfPlayerWins(@Bind("name") String name);

    /**
     * Gets how many times the given Player lost.
     * @param name given Player's name
     * @return how many times the given Player lost.
     */
    @SqlQuery("select loseCount from LeaderBoard where name = :name")
    int numberOfPlayerLoses(@Bind("name") String name);

    /**
     * Gets all Player's info from the database, ordered by winCount and loseCount.
     * @return a {@code PlayerInfo} array, containing these informations.
     */
    @SqlQuery("select name, winCount, loseCount from LeaderBoard order by winCount desc, loseCount ")
    PlayerInfo[] getPlayersOrdered();

    /**
     * Checks if a Player already exists in the database.
     * @param name given Player's name
     * @return if it exists or not
     */
    @SqlQuery("select exists (select * from LeaderBoard where name = (:name))")
    boolean playerExists(@Bind("name") String name);

    /**
     * Deletes all data from the LeaderBoard table.
     */
    @SqlUpdate("delete from LeaderBoard")
    void deletePlayerInfos();
}
