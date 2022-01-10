package Manager;

import Tool.ChangeIconSize;
import Tool.JdbcUtils;
import Tool.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Deal extends JFrame implements ActionListener {
    private JTable table;
    private MyTableModel tablemodel;
    private JButton b1;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Deal() throws SQLException {
        this.setSize(400,300);
        this.setTitle("成交管理");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setEnabled(false);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        this.getContentPane().add(scroll,BorderLayout.CENTER);

        b1 = new JButton();
        b1 = new ChangeIconSize().ChangIconSize(b1,"img\\add.png",15,15,"添加");
        b1.addActionListener(this);
        JToolBar toolBar = new JToolBar();
        toolBar.add(b1);
        this.getContentPane().add(toolBar,BorderLayout.NORTH);
        this.setVisible(true);
    }



    public MyTableModel getModel() throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        conn = JdbcUtils.getConnection();
        String sql = "select * from deal";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);

        String[] Column = {"房源编号","员工编号","客户编号","房主编号","成交价格","成交时间"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<DealMessage> v = new ArrayList<>();
        while (rs.next()){
            DealMessage deal = new DealMessage();
            deal.setZID(rs.getInt("ZID"));
            deal.setNo(rs.getInt("No"));
            deal.setCID(rs.getInt("CID"));
            deal.setOID(rs.getInt("OID"));
            deal.setPrice(rs.getString("price"));
            deal.setTime(rs.getString("Time"));
            v.add(deal);
        }

        for (i = 0;i<v.size();i++){
            tableModel.addRow(new Object[]
                    {       v.get(i).getZID(),
                            v.get(i).getNo(),
                            v.get(i).getCID(),
                            v.get(i).getOID(),
                            v.get(i).getPrice(),
                            v.get(i).getTime()
                    });
        }
        JdbcUtils.close(conn,ps,rs);
        return tableModel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            new DealAdd();
        }
    }
}
