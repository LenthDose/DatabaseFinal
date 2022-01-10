package Register;

import Tool.DateTime;
import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerRegister extends JFrame implements ActionListener {
    private JTextField Name, Phone, Sex, UserName, ID, Email;
    private JPasswordField Password, Password2;
    private Connection conn;
    private PreparedStatement ps;
    private int count;

    public OwnerRegister() {
        this.setSize(350, 350);
        this.setTitle("注册");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(9, 2));
        this.getContentPane().add(new JLabel("用户名"));
        UserName = new JTextField();
        this.getContentPane().add(UserName);
        this.getContentPane().add(new JLabel("密码"));
        Password = new JPasswordField();
        this.getContentPane().add(Password);
        this.getContentPane().add(new JLabel("确认密码"));
        Password2 = new JPasswordField();
        this.getContentPane().add(Password2);
        this.getContentPane().add(new JLabel("姓名"));
        Name = new JTextField();
        this.getContentPane().add(Name);
        this.getContentPane().add(new JLabel("性别"));
        Sex = new JTextField();
        this.getContentPane().add(Sex);
        this.getContentPane().add(new JLabel("身份证号码"));
        ID = new JTextField();
        this.getContentPane().add(ID);
        this.getContentPane().add(new JLabel("邮箱"));
        Email = new JTextField();
        this.getContentPane().add(Email);
        this.getContentPane().add(new JLabel("联系方式"));
        Phone = new JTextField();
        this.getContentPane().add(Phone);
        JButton confirm = new JButton("确认");
        this.getContentPane().add(confirm);
        confirm.addActionListener(this);
        JButton cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);
        this.setVisible(true);
    }


    public void Register() throws SQLException {
        String username = UserName.getText();
        String name = Name.getText();
        String password = new String(Password.getPassword());
        String sex = Sex.getText();
        String email = Email.getText();
        String phone = Phone.getText();
        String id = ID.getText();
        String password2 = new String(Password2.getPassword());
        if (password.equals(password2)) {
            try {
                conn = JdbcUtils.getConnection();
                String sql = "insert into ouser values (null,'" + username + "','" + password + "','" + password2 + "','" +
                        name + "','" + sex + "','" + id + "','" + email + "','" + phone + "','" + DateTime.getDate() + "')";
                ps = conn.prepareStatement(sql);
                count = ps.executeUpdate(sql);
                if (count != 0) {
                    JOptionPane.showMessageDialog(this, "注册成功", "提示", JOptionPane.OK_OPTION);
                    if (JOptionPane.OK_OPTION == 0) {
                        this.setVisible(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "注册失败", "提示", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                JdbcUtils.close(conn, ps);
            }
        } else {
            JOptionPane.showMessageDialog(this, "两次输入的密码不同,请重新输入", "提示", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean check() throws SQLException {
        String username = UserName.getText();
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = " select * from ouser where uid='" + username + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "用户名已存在,请重新输入", "提示", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(conn, ps, rs);
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getActionCommand().equals("确认")) {
            try {
                if (check()) {
                    Register();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (e.getActionCommand().equals("取消")) {
            this.setVisible(false);
        }

    }
}
