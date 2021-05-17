package boardgame.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(PlayerInfo.class)
public interface LeaderBoardDAO {
    @SqlUpdate("""
            create table LeaderBoard(
                name varchar2 primary key,
                winCount int not null,
                loseCount int not null)
            """)
    void createTable();

    @SqlUpdate("insert into LeaderBoard values(:name, 1, 0)")
    void insertWinnerPlayer(@Bind("name") String name);

    @SqlUpdate("insert into LeaderBoard values(:name, 0, 1)")
    void insertLoserPlayer(@Bind("name") String name);

    @SqlUpdate("update LeaderBoard set winCount = winCount + 1 where name = :name ")
    void incrementPlayerWins(@Bind("name") String name);

    @SqlUpdate("update LeaderBoard set loseCount = loseCount + 1 where name = :name ")
    void incrementPlayerLoses(@Bind("name") String name);

    @SqlQuery("select winCount from LeaderBoard where name = :name")
    int numberOfPlayerWins(@Bind("name") String name);

    @SqlQuery("select loseCount from LeaderBoard where name = :name")
    int numberOfPlayerLoses(@Bind("name") String name);

    @SqlQuery("select name, winCount, loseCount from LeaderBoard order by winCount desc, loseCount ")
    PlayerInfo[] getPlayersOrdered();

    @SqlQuery("select exists (select * from LeaderBoard where name = (:name))")
    boolean playerExists(@Bind("name") String name);

    @SqlUpdate("delete from LeaderBoard")
    void deletePlayerInfos();
}
