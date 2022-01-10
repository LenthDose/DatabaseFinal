package Login;

import Customer.Main;
import Tool.JdbcUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * @author Silhouette76
 */
public class CustomerLogin extends JFrame implements ActionListener {
    private JTextField username;
    private JPasswordField password;
    private JButton login,cancel;



    public CustomerLogin(){
        this.setSize(350,250);
        this.setTitle("用户登录");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(3,2));
        this.getContentPane().add(new JLabel("用户名"));
        username = new JTextField(10);
        this.getContentPane().add(username);
        this.getContentPane().add(new JLabel("密码"));
        password = new JPasswordField(10);
        this.getContentPane().add(password);

        login = new JButton("登录");
        login.addActionListener(this);
        this.getContentPane().add(login);
        cancel = new JButton("取消");
        cancel.addActionListener(this);
        this.getContentPane().add(cancel);

        this.setVisible(true);
    }

    public boolean login() throws Exception {
        String pass = new String(password.getPassword());
        String user = username.getText();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JdbcUtils.getConnection();
            String sql = " select * from cuser where uid='" + user + "'and pwd = '" + pass + "'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(this,"登录成功","提示",JOptionPane.OK_OPTION);
                if(JOptionPane.OK_OPTION == 0) {
                    this.setVisible(false);
                }
                return true;
            }
            else {
                JOptionPane.showMessageDialog(this,"请重新输入正确的用户和密码","提示",JOptionPane.ERROR_MESSAGE);
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

        String user = username.getText();

        if(e.getSource() == login){
            try {
                if(login()){
                    new Main(user);
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
