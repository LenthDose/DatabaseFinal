package Customer;

import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChooseHouse extends JFrame implements ActionListener{
    private int ZID;
    private String uid;
    private JButton confirm;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private JComboBox Year,Month,Day;
    private static String[] month = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private static String[] day = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    public ChooseHouse(int zid,String UID) {
        this.ZID = zid;
        this.uid = UID;
        this.setSize(500,500);
        this.setTitle("选择看房时间");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        this.getContentPane().setLayout(new GridLayout(3,1));
        JLabel label = new JLabel("看房时间", JLabel.CENTER);
        label.setFont(new Font("宋体", Font.PLAIN, 25));
        this.getContentPane().add(label);

        JPanel datePanel = new JPanel(new GridLayout(1, 6));

        datePanel.add(Year = new JComboBox<>());
        Year.addItem("2020");
        datePanel.add(new JLabel("年"));
        datePanel.add(this.Month = new JComboBox<>(month));
        datePanel.add(new JLabel("月"));
        datePanel.add(this.Day = new JComboBox<>(day));
        datePanel.add(new JLabel("日"));
        this.getContentPane().add(datePanel,BorderLayout.CENTER);

        confirm = new JButton("确认");
        confirm.addActionListener(this);
        this.getContentPane().add(confirm);

        this.setVisible(true);
    }

    public void confirm() throws SQLException {
        String time = Year.getSelectedItem()+"-"+Month.getSelectedItem()+"-"+Day.getSelectedItem();
        int No = FindNo();
        int CID = FindID();
        if(No != 0) {
            try {
                conn = JdbcUtils.getConnection();
                String sql = "insert into choose values(null,ZID ,No, CID ,'" + time + "')";
                ps = conn.prepareStatement(sql);
                int count = ps.executeUpdate(sql);
                if(count != 0)
                {
                    JOptionPane.showMessageDialog(this,"登记成功，经纪人稍后会与您联系","提示",JOptionPane.OK_OPTION);
                    if(JOptionPane.OK_OPTION == 0){
                        this.setVisible(false);
                    }
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            } finally {
                JdbcUtils.close(conn, ps);
            }
        }

    }

    public int FindNo() throws SQLException {
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select no from sale where ZID = " + ZID;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("no");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return 0;
    }

    public int FindID() throws SQLException {
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select ID from cuser where uid = '" + uid +"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            if(e.getSource() == confirm){
                try {
                    confirm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
    }

}
