package Manager;

import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DealAdd extends JFrame implements ActionListener {
    private JTextField ZID,CID,OID,No,Price,Time;
    private JButton confirm;
    private Connection conn;
    private PreparedStatement ps;


    public DealAdd() {
        this.setSize(350,350);
        this.setTitle("出售房屋登记");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(7,2));
        this.getContentPane().add(new JLabel("房屋编号"));
        ZID = new JTextField();
        this.getContentPane().add(ZID);
        this.getContentPane().add(new JLabel("员工编号"));
        No = new JTextField();
        this.getContentPane().add(No);
        this.getContentPane().add(new JLabel("客户编号"));
        CID = new JTextField();
        this.getContentPane().add(CID);
        this.getContentPane().add(new JLabel("业主编号"));
        OID = new JTextField();
        this.getContentPane().add(OID);
        this.getContentPane().add(new JLabel("成交价格"));
        Price = new JTextField();
        this.getContentPane().add(Price);
        this.getContentPane().add(new JLabel("成交时间"));
        Time = new JTextField();
        this.getContentPane().add(Time);

        confirm = new JButton("确认录入");
        this.getContentPane().add(confirm);
        confirm.addActionListener(this);
        this.setVisible(true);
    }

    public boolean Confirm() throws SQLException {
        String zid = ZID.getText();
        String no = No.getText();
        String cid = CID.getText();
        String oid = OID.getText();
        String price = Price.getText();
        String time = Time.getText();

        try{
            conn = JdbcUtils.getConnection();
            String sql = "insert into deal values(null" + zid +"," + no + "," + cid + ","+ oid +",'" + price + "','" + time +"')";
            ps = conn.prepareStatement(sql);
            int count = ps.executeUpdate(sql);
            if(count != 0){
                JOptionPane.showMessageDialog(this,"录入成功","提示",JOptionPane.OK_OPTION);
                if(JOptionPane.OK_OPTION == 0){
                    this.setVisible(false);
                }
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps);
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String zid = ZID.getText();
            if(e.getSource() == confirm){
                try {
                    if(Confirm()){
                        new DeleteHouse(zid);
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
    }

}
