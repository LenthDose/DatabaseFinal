package Owner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Main extends JFrame implements ActionListener {
    private JButton b1,b5;
    private String UserName;
    public Main(String username) {
        this.UserName = username;
        this.setSize(450,300);
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(2,1));

        b1 = new JButton("出租、售房源登记");
        b5 = new JButton("修改个人信息");

        this.getContentPane().add(b1);
        this.getContentPane().add(b5);

        b1.addActionListener(this);
        b5.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b1){
            new Sale(UserName);
        }

        else if(e.getSource() == b5){
            try {
                new ChangeHouseMessage(UserName);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
