package Owner;

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

public class Sale extends JFrame implements ActionListener{
        private String UserName;
        private JTextField City,HouseType,Diduan,Taox,Lc,Tc,Zx,MianJi,Jiage,Xiaoqu,Chanq,Tel,BuildingDate,person;
        private JButton register,cancel;
        private Connection conn;
        private PreparedStatement ps;
        private int count;
        private ResultSet rs;


        public Sale(String userName) {
            this.UserName = userName;
            this.setSize(350,350);
            this.setTitle("出售房屋登记");
            this.setLocationRelativeTo(getOwner());
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            this.getContentPane().setLayout(new GridLayout(15,2));
            this.getContentPane().add(new JLabel("城市"));
            City = new JTextField();
            this.getContentPane().add(City);
            this.getContentPane().add(new JLabel("地段"));
            Diduan = new JTextField();
            this.getContentPane().add(Diduan);
            this.getContentPane().add(new JLabel("房屋类型"));
            HouseType = new JTextField();
            this.getContentPane().add(HouseType);
            this.getContentPane().add(new JLabel("户型布局"));
            Taox = new JTextField();
            this.getContentPane().add(Taox);
            this.getContentPane().add(new JLabel("当前楼层"));
            Lc = new JTextField();
            this.getContentPane().add(Lc);
            this.getContentPane().add(new JLabel("总楼层"));
            Tc = new JTextField();
            this.getContentPane().add(Tc);
            this.getContentPane().add(new JLabel("装修"));
            Zx = new JTextField();
            this.getContentPane().add(Zx);
            this.getContentPane().add(new JLabel("面积(平米)"));
            MianJi = new JTextField();
            this.getContentPane().add(MianJi);
            this.getContentPane().add(new JLabel("价格"));
            Jiage = new JTextField();
            this.getContentPane().add(Jiage);
            this.getContentPane().add(new JLabel("小区"));
            Xiaoqu = new JTextField();
            this.getContentPane().add(Xiaoqu);
            this.getContentPane().add(new JLabel("产权"));
            Chanq = new JTextField();
            this.getContentPane().add(Chanq);
            this.getContentPane().add(new JLabel("联系电话"));
            Tel = new JTextField();
            this.getContentPane().add(Tel);
            this.getContentPane().add(new JLabel("房产建成日期"));
            BuildingDate = new JTextField();
            this.getContentPane().add(BuildingDate);
            this.getContentPane().add(new JLabel("联系人"));
            person = new JTextField();
            this.getContentPane().add(person);

            register = new JButton("确认发布");
            this.getContentPane().add(register);
            register.addActionListener(this);
            cancel = new JButton("取消");
            cancel.addActionListener(this);
            this.getContentPane().add(cancel);
            this.setVisible(true);
        }

    public int FindID() throws SQLException {
        try{
            conn = JdbcUtils.getConnection();
            String sql = "select ID from ouser where uid = '" + UserName +"'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery(sql);
            if(rs.next()){
                return rs.getInt("ID");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return 0;
    }

        public void register() throws SQLException {
            String city = City.getText();
            String housetype = HouseType.getText();
            String diduan = Diduan.getText();
            String taox = Taox.getText();
            String lc = Lc.getText();
            String tc = Tc.getText();
            String zx = Zx.getText();
            String mianji = MianJi.getText();
            String jiage = Jiage.getText();
            String chanq = Chanq.getText();
            String tel = Tel.getText();
            String bd = BuildingDate.getText();
            String p = person.getText();
            String xiaoqu = Xiaoqu.getText();
            try {
                int OID = FindID();
                conn = JdbcUtils.getConnection();
                String sql = "Insert into sale values(null,OID,'"+city + "','" +housetype +"','" + diduan + "','"
                        + taox + "','" + lc + "','" + tc + "','"+ zx + "','" + mianji + "','"
                        + jiage + "','" + xiaoqu + "','" + chanq + "','" + tel + "','" + DateTime.getDate() +"','" + bd + "','" + p + "',null)";
                String sql1 = "update sale set no = (select no from manager order by rand() limit 1) where OID = '" + OID +"'";
                ps = conn.prepareStatement(sql);
                ps = conn.prepareStatement(sql1);
                count = ps.executeUpdate(sql);
                count = ps.executeUpdate(sql1);
                if (count != 0) {
                    JOptionPane.showMessageDialog(this,"发布成功","提示",JOptionPane.OK_OPTION);
                    if (JOptionPane.OK_OPTION == 0) {
                        this.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(this,"发布失败","提示",JOptionPane.ERROR_MESSAGE);
                    }
                }
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
