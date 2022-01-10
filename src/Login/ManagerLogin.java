package Login;

import Manager.Main;
import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerLogin extends JFrame implements ActionListener {
    private JTextField No;
    private JPasswordField Password;
    private JButton login,cancel;

    public ManagerLogin() {
        this.setSize(350,250);
        this.setTitle("员工登录");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(3,2));
        this.getContentPane().add(new JLabel("员工编号"));
        No = new JTextField();
        this.getContentPane().add(No);
        this.getContentPane().add(new JLabel("密码"));
        Password = new JPasswordField(10);
        this.getContentPane().add(Password);
        login = new JButton("登录");
        login.addActionListener(this);
        this.getContentPane().add(login);
        cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);

        this.setVisible(true);
    }

    public boolean login() throws Exception {
        String pass = new String(Password.getPassword());
        String no = No.getText();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            String sql = " select * from readerinformation where userName='" + no + "'and password = '" + pass + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.OK_OPTION);
                if(JOptionPane.OK_OPTION == 0){
                    this.setVisible(false);
                }
                return true;
            }
            else {
                JOptionPane.showMessageDialog(this,"修改失败","提示",JOptionPane.ERROR_MESSAGE);
                return false;
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
        String no = No.getText();
        if(e.getSource() == login){
            try {
                if(login()) {
                    new Main(no);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == cancel){
            this.setVisible(false);
        }
    }


}
