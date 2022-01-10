package Manager;


import Customer.CheckHouse;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class Main extends JFrame implements ActionListener {
        private String No;

    public Main(String no) {
        this.No = no;
        this.setSize(450,300);
        this.setTitle("房产中介管理系统");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(5,1));
        JButton b1 = new JButton("房源信息管理");
        this.getContentPane().add(b1);
        b1.addActionListener(this);
        JButton b2 = new JButton("客户信息管理");
        this.getContentPane().add(b2);
        b2.addActionListener(this);
        JButton b3 = new JButton("成交管理");
        this.getContentPane().add(b3);
        b3.addActionListener(this);
        JButton b4 =  new JButton("业务跟进");
        b4.addActionListener(this);
        this.getContentPane().add(b4);
        JButton b5 = new JButton("文档管理");
        b5.addActionListener(this);
        this.getContentPane().add(b5);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("房源信息管理")){
            try {
                new HouseControl(No);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("客户信息管理")){
            try {
                new CheckCustomer();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("业务跟进")){
            try {
                new Follow();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("成交管理")){
            try {
                new Deal();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("文档管理")){
            try {
                new FileOpen();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
