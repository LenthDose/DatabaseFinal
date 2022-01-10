package Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Main extends JFrame implements ActionListener {
    private JButton b3,b4,b5;
    private String UserName;
    public Main(String username) {
        this.UserName = username;
        this.setSize(450,300);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(3,1));


        b3 = new JButton("求购、租客户登记");
        b4 = new JButton("查询房源");
        b5 = new JButton("修改个人信息");

        this.getContentPane().add(b3);
        this.getContentPane().add(b4);
        this.getContentPane().add(b5);

        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if(e.getSource() == b3){
            new Need(UserName);
        }
        else if(e.getSource() == b4){
            try {
                new CheckHouse(UserName);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == b5){
           try {
               new ChangeNeedMeassage(UserName);
           } catch (SQLException exception) {
               exception.printStackTrace();
           }
       }
    }
}
