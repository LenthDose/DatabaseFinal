package Manager;

import Tool.JdbcUtils;
import Tool.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Follow extends JFrame {
    private JTable table;
    private MyTableModel tablemodel;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Follow() throws SQLException {
        this.setSize(400,300);
        this.setTitle("业务跟进");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setEnabled(false);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        this.getContentPane().add(scroll,BorderLayout.CENTER);

        this.setVisible(true);
    }

    public MyTableModel getModel() throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        conn = JdbcUtils.getConnection();
        String sql = "select * from choose";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);

        String[] Column = {"房屋编号","员工编号","客户编号","看房时间"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<FollowMessage> v = new ArrayList<>();
        while (rs.next()){
            FollowMessage follow = new FollowMessage();
            follow.setZID(rs.getString("ZID"));
            follow.setNo(rs.getString("No"));
            follow.setUID(rs.getString("CID"));
            follow.setTime(rs.getString("Time"));
            v.add(follow);
        }

        for (i = 0;i<v.size();i++){
            tableModel.addRow(new Object[]
                    {       v.get(i).getZID(),
                            v.get(i).getNo(),
                            v.get(i).getUID(),
                            v.get(i).getTime()
                    });
        }
        JdbcUtils.close(conn,ps,rs);
        return tableModel;
    }
}
