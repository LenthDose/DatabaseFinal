import Login.CustomerLogin;
import Login.ManagerLogin;
import Login.OwnerLogin;
import Register.CustomerRegister;
import Register.ManagerRegister;
import Register.OwnerRegister;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {

    public Main() {
        this.setSize(300,300);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(6,1));
        JButton b5 = new JButton("业主登录");
        JButton b6 = new JButton("业主注册");
        JButton b1 = new JButton("客户登录");
        JButton b2 = new JButton("客户注册");
        JButton b3 = new JButton("员工登录");
        JButton b4 = new JButton("员工注册");
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        this.getContentPane().add(b5);
        this.getContentPane().add(b6);
        this.getContentPane().add(b1);
        this.getContentPane().add(b2);
        this.getContentPane().add(b3);
        this.getContentPane().add(b4);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("客户登录")){
            new CustomerLogin();

        }
        else if(e.getActionCommand().equals("客户注册")){
            new CustomerRegister();

        }
        else if(e.getActionCommand().equals("员工登录")){
            new ManagerLogin();

        }
        else if(e.getActionCommand().equals("员工注册")){
            new ManagerRegister();
        }
        else if(e.getActionCommand().equals("业主登录")){
            new OwnerLogin();
        }
        else if(e.getActionCommand().equals("业主注册")){
            new OwnerRegister();
        }
    }
}
