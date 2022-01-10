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

public class CheckCustomer extends JFrame implements ActionListener {
    private JTable table;
    private MyTableModel tablemodel;
    private int No;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public CheckCustomer() throws SQLException {
        this.setSize(1500,300);
        this.setTitle("客户信息");
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
        String sql = "select * from cuser ";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);

        String[] Column = {"客户编号","用户名","姓名","性别","身份证","邮箱","联系电话","注册时间"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<CustomerMessage> v = new ArrayList<>();
        while (rs.next()){
            CustomerMessage customer = new CustomerMessage();
            customer.setID(rs.getString("ID"));
            customer.setUid(rs.getString("uid"));
            customer.setuName(rs.getString("uName"));
            customer.setuSex(rs.getString("uSex"));
            customer.setuSfz(rs.getString("usfz"));
            customer.setEmail(rs.getString("email"));
            customer.setTel(rs.getString("Tel"));
            customer.setDate(rs.getString("Date"));
            v.add(customer);
        }

        for (i = 0;i<v.size();i++){
            tableModel.addRow(new Object[]
                    {       v.get(i).getID(),
                            v.get(i).getUid(),
                            v.get(i).getuName(),
                            v.get(i).getuSex(),
                            v.get(i).getuSfz(),
                            v.get(i).getEmail(),
                            v.get(i).getTel(),
                            v.get(i).getDate()
                    });
        }
        JdbcUtils.close(conn,ps,rs);
        return tableModel;
    }
//
//
//    public MyTableModel getSearchModel(String table) throws SQLException {
//        MyTableModel tableModel = new MyTableModel();
//        conn = JdbcUtils.getConnection();
//        String sql = "select * from ouser,cuser where "+ table + "=" + search.getText();
//        ps = conn.prepareStatement(sql);
//        rs = ps.executeQuery(sql);
//
//        String[] Column = {"客户编号","用户名","姓名","性别","身份证","邮箱","联系电话","注册时间"};
//        int i;
//        for (i = 0;i<Column.length;i++){
//            tableModel.addColumn(Column[i]);
//        }
//
//        ArrayList<CustomerMessage> v = new ArrayList<>();
//        while (rs.next()){
//            CustomerMessage customer = new CustomerMessage();
//            customer.setID(rs.getString("ID"));
//            customer.setUid(rs.getString("uid"));
//            customer.setuName(rs.getString("Name"));
//            customer.setuSex(rs.getString("Sex"));
//            customer.setuSfz(rs.getString("usfz"));
//            customer.setEmail(rs.getString("email"));
//            customer.setTel(rs.getString("Tel"));
//            customer.setDate(rs.getString("Date"));
//            v.add(customer);
//        }
//
//        for (i = 0;i<v.size();i++){
//            tableModel.addRow(new Object[]
//                    {       v.get(i).getID(),
//                            v.get(i).getUid(),
//                            v.get(i).getuName(),
//                            v.get(i).getuSex(),
//                            v.get(i).getuSfz(),
//                            v.get(i).getEmail(),
//                            v.get(i).getDate()
//
//                    });
//        }
//        JdbcUtils.close(conn,ps,rs);
//        return tableModel;
//    }


    public void rush() throws SQLException {
        tablemodel = getModel();
        table.setModel(tablemodel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


}
