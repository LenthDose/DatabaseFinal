package Tool;

import java.sql.*;

/**
 * @author Silhouette76
 */
public class JdbcUtils {

    private static final String USER = "root";
    private static final String PWD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/library?useSSL=false";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PWD);
    }

    public static void close(Connection conn, Statement stmt) throws SQLException {
        if(stmt !=  null){
            stmt.close();
        }
        if (conn !=  null){
            conn.close();
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
        if(rs != null){
            rs.close();
        }
       close(conn, stmt);
    }

}
