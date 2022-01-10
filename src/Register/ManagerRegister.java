package Register;

import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerRegister extends JFrame implements ActionListener {
    private JTextField Name,Sex,Phone,No;
    private JPasswordField password;
    private JButton register;
    private Connection conn;
    private PreparedStatement ps;
    private int count;

    public ManagerRegister() {
        this.setSize(400,300);
        this.setTitle("员工注册");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(6,2));
        this.getContentPane().add(new JLabel("工号"));
        No = new JTextField(10);
        this.getContentPane().add(No);
        this.getContentPane().add(new JLabel("密码"));
        password = new JPasswordField(10);
        this.getContentPane().add(password);
        this.getContentPane().add(new JLabel("姓名"));
        Name = new JTextField(10);
        this.getContentPane().add(Name);
        this.getContentPane().add(new JLabel("性别"));
        Sex = new JTextField(10);
        this.getContentPane().add(Sex);
        this.getContentPane().add(new JLabel("联系电话"));
        Phone = new JTextField(10);
        this.getContentPane().add(Phone);
        register = new JButton("确认");
        this.getContentPane().add(register);
        register.addActionListener(this);
        JButton cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
        this.setVisible(true);
    }

    public void Register() throws SQLException {
        String no = No.getText();
        String pass = new String(password.getPassword());
        String name = Name.getText();
        String sex =  Sex.getText();
        String phone = Phone.getText();

        try{
            conn = JdbcUtils.getConnection();
            String sql = "insert into manager values ('" + no +"','" + pass +"','"+ name +"','" + sex +"','"+ phone +"')";
            ps = conn.prepareStatement(sql);
            count = ps.executeUpdate(sql);
            if(count != 0){
                JOptionPane.showMessageDialog(this,"注册成功","提示",JOptionPane.OK_OPTION);
                this.setVisible(false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            JdbcUtils.close(conn,ps);
        }
    }

    public boolean check() throws SQLException {
        ResultSet rs = null;
        String no = No.getText();
        try{
            conn = JdbcUtils.getConnection();
            String sql = " select * from manager where No='" + no +"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(this,"用户已存在,请重新登录","提示",JOptionPane.ERROR_MESSAGE);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == register) {
            try {
                if (!check()) {
                    try {
                        Register();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("取消")){
            this.setVisible(false);
        }
    }
}
