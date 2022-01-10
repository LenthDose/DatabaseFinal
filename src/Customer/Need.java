package Customer;

import Tool.DateTime;
import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Need extends JFrame implements ActionListener {
    private String UserName;
    private JTextField City,HouseType,Taox,Zx,MianJi,Jiage,Tel;
    private JButton register,cancel;
    private Connection conn;
    private PreparedStatement ps;
    private int count;

    public Need(String username) {
        this.UserName = username;
        this.setSize(350,350);
        this.setTitle("求购、租客户登记34");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(8,2));
        this.getContentPane().add(new JLabel("城市"));
        City = new JTextField();
        this.getContentPane().add(City);
        this.getContentPane().add(new JLabel("房屋类型"));
        HouseType = new JTextField();
        this.getContentPane().add(HouseType);
        this.getContentPane().add(new JLabel("户型布局"));
        Taox = new JTextField();
        this.getContentPane().add(Taox);
        this.getContentPane().add(new JLabel("装修"));
        Zx = new JTextField();
        this.getContentPane().add(Zx);
        this.getContentPane().add(new JLabel("面积(平米)"));
        MianJi = new JTextField();
        this.getContentPane().add(MianJi);
        this.getContentPane().add(new JLabel("价格"));
        Jiage = new JTextField();
        this.getContentPane().add(Jiage);
        this.getContentPane().add(new JLabel("联系电话"));
        Tel = new JTextField();
        this.getContentPane().add(Tel);

        register = new JButton("确认发布");
        this.getContentPane().add(register);
        register.addActionListener(this);
        cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
        this.setVisible(true);
    }


    public void register() throws SQLException {
        String city = City.getText();
        String housetype = HouseType.getText();
        String taox = Taox.getText();
        String zx = Zx.getText();
        String mianji = MianJi.getText();
        String jiage = Jiage.getText();
        String tel = Tel.getText();

        try {
            conn = JdbcUtils.getConnection();
            String sql = "Insert into need values(null,'" + UserName + "','" + city + "','" + housetype +"','"+ taox + "','"+ zx + "','" + tel + "','"
                    + mianji + "','" + jiage + "','" + DateTime.getDate() + "',null)";
            ps = conn.prepareStatement(sql);
            count = ps.executeUpdate(sql);
            if (count != 0) {
                insertNo();
                JOptionPane.showMessageDialog(this,"发布成功","提示",JOptionPane.OK_OPTION);
                this.setVisible(false);
            }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            finally {
                JdbcUtils.close(conn,ps);
            }
    }

    public void insertNo() throws SQLException {
        try{
            conn = JdbcUtils.getConnection();
            String sql = "update need set no = (select no from manager order by rand() limit 10) where uid = '" + UserName + "'";
            ps = conn.prepareStatement(sql);
            count = ps.executeUpdate(sql);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == register){
            try {
                register();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == cancel){
            this.setVisible(false);
        }
    }

}
