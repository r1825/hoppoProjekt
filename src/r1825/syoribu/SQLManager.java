package r1825.syoribu;

import java.sql.*;

public class SQLManager {

    private static boolean isEnable = true;

    private static final String HOST   = "dzyan.local";
    private static final int    PORT   = 5432;
    private static final String DBNAME = "hamako-fest-2019";
    private static final String URL    = "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DBNAME;
    private static final String USER   = "postgres";
    private static final String PASSWD = "passwd";

    public static boolean insertResult (int userId, int score) throws Exception {
        if ( !isEnable ) return true;
        Connection connection = null;
        Statement statement = null;

        try {

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection( URL, USER, PASSWD );

            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql;
            sql = String.format("insert into results ( user_id, game_id, score ) values( %d, %d, %d )", userId, 0, score);
            statement.executeUpdate(sql);
            connection.commit();
        }
        catch ( SQLException e ) {
            e.printStackTrace();
            connection.rollback();
            throw e;
        }
        finally {
            if ( statement != null ) statement.close();
            if ( connection != null ) connection.close();
        }

        return true;
    }
}
