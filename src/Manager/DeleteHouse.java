package Manager;

import Tool.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteHouse {
    private Connection conn;
    private PreparedStatement ps;

    public DeleteHouse(String ZID) throws SQLException {
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update sale set situation = '已成交' where ZID = '"+ ZID +"'";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps);
        }
    }
}
