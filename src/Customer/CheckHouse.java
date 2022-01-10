package Customer;

import Manager.HouseMessage;
import Tool.ChangeIconSize;
import Tool.JdbcUtils;
import Tool.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CheckHouse extends JFrame implements ActionListener {
    private JTable table;
    private MyTableModel tablemodel;
    private  JButton b4,b5,b6;
    private JComboBox comBox;
    private JTextField search;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private String UID;

    public CheckHouse(String username) throws SQLException {
        this.UID = username;
        this.setSize(1000,500);
        this.setTitle("房源信息");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        this.getContentPane().add(scroll,BorderLayout.CENTER);


        b4 = new JButton();
        b4 = new ChangeIconSize().ChangIconSize(b4,"img\\search.png",15,15,"搜索");
        b4.addActionListener(this);
        search = new JTextField();
        b5 = new JButton();
        b5 = new ChangeIconSize().ChangIconSize(b4,"img\\refresh.png",15,15,"刷新");
        b5.addActionListener(this);
        String[] items = {"城市","户型","房屋类型"};
        comBox = new JComboBox(items);
        b6 = new JButton("选择看房时间");
        b6.addActionListener(this);


        JToolBar toolBar1 = new JToolBar();
        toolBar1.add(b5);
        toolBar1.add(comBox);
        toolBar1.add(search);
        toolBar1.add(b4);
        toolBar1.add(b6);
        this.getContentPane().add(toolBar1,BorderLayout.NORTH);
        this.setVisible(true);
    }

    public MyTableModel getModel() throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        conn = JdbcUtils.getConnection();
        String sql = "select * from sale";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);

        String[] Column = {"房屋编号","城市","房屋类型","地段","户型","当前楼层","总楼层","装修","面积(平米)","价格","小区","产权","电话","房源注册日期","房产建成日期","业主","房源状态"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<HouseMessage> v = new ArrayList<>();
        while (rs.next()){
            HouseMessage house = new HouseMessage();
            house.setZID(rs.getInt("ZID"));
            house.setCity(rs.getString("City"));
            house.setHouseType(rs.getString("housetype"));
            house.setDiduan(rs.getString("Diduan"));
            house.setTaox(rs.getString("Taox"));
            house.setLc(rs.getString("Lc"));
            house.setTc(rs.getString("Tc"));
            house.setZx(rs.getString("Zx"));
            house.setMianji(rs.getString("Mianj"));
            house.setJiage(rs.getString("Jiag"));
            house.setXiaoqu(rs.getString("Xiaoq"));
            house.setChanq(rs.getString("Chanq"));
            house.setTel(rs.getString("Tel"));
            house.setDate(rs.getString("date"));
            house.setBd(rs.getString("BD"));
            house.setPerson(rs.getString("person"));
            house.setCondition(rs.getString("situation"));
            v.add(house);
        }

        for (i = 0;i<v.size();i++){
            tableModel.addRow(new Object[]
                    {
                            v.get(i).getZID(),
                            v.get(i).getCity(),
                            v.get(i).getHouseType(),
                            v.get(i).getDiduan(),
                            v.get(i).getTaox(),
                            v.get(i).getLc(),
                            v.get(i).getTc(),
                            v.get(i).getZx(),
                            v.get(i).getMianji(),
                            v.get(i).getJiage(),
                            v.get(i).getXiaoqu(),
                            v.get(i).getChanq(),
                            v.get(i).getTel(),
                            v.get(i).getDate(),
                            v.get(i).getBd(),
                            v.get(i).getPerson(),
                            v.get(i).getCondition()
                    });
        }
        JdbcUtils.close(conn,ps,rs);
        return tableModel;
    }

    public MyTableModel getSearchModel(String table) throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        conn = JdbcUtils.getConnection();
        String sql = "select * from sale where " + table +" = '" + search.getText() +"'";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);

        String[] Column = {"房屋编号","城市","房屋类型","地段","户型","当前楼层","总楼层","装修","面积(平米)","价格","小区","产权","电话","房源注册日期","房产建成日期","业主","房源状态"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<HouseMessage> v = new ArrayList<>();
        while (rs.next()){
            HouseMessage house = new HouseMessage();
            house.setZID(rs.getInt("ZID"));
            house.setCity(rs.getString("City"));
            house.setHouseType(rs.getString("housetype"));
            house.setDiduan(rs.getString("Diduan"));
            house.setTaox(rs.getString("Taox"));
            house.setLc(rs.getString("Lc"));
            house.setTc(rs.getString("Tc"));
            house.setZx(rs.getString("Zx"));
            house.setMianji(rs.getString("Mianj"));
            house.setJiage(rs.getString("Jiag"));
            house.setXiaoqu(rs.getString("Xiaoq"));
            house.setChanq(rs.getString("Chanq"));
            house.setTel(rs.getString("Tel"));
            house.setDate(rs.getString("date"));
            house.setBd(rs.getString("BD"));
            house.setPerson(rs.getString("person"));
            house.setCondition(rs.getString("situation"));
            v.add(house);
        }

        for (i = 0;i<v.size();i++){
            tableModel.addRow(new Object[]
                    {
                            v.get(i).getZID(),
                            v.get(i).getCity(),
                            v.get(i).getHouseType(),
                            v.get(i).getDiduan(),
                            v.get(i).getTaox(),
                            v.get(i).getLc(),
                            v.get(i).getTc(),
                            v.get(i).getZx(),
                            v.get(i).getMianji(),
                            v.get(i).getJiage(),
                            v.get(i).getXiaoqu(),
                            v.get(i).getChanq(),
                            v.get(i).getTel(),
                            v.get(i).getDate(),
                            v.get(i).getBd(),
                            v.get(i).getPerson(),
                            v.get(i).getCondition()
                    });
        }
        JdbcUtils.close(conn,ps,rs);
        return tableModel;
    }

    public void rush() throws SQLException {
        tablemodel = getModel();
        table.setModel(tablemodel);
    }

    public void ChooseHouse(){
        if(table.getSelectedRows().length > 0) {
            int selRowIndexs = table.getSelectedRow();
            int ZID = (Integer) table.getValueAt(selRowIndexs,0);
            new ChooseHouse(ZID,UID);
        }
        else {
            JOptionPane.showMessageDialog(this,"请选择想要的房源","提示",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b4){
            try {
                if(comBox.getSelectedItem().equals("城市")) {
                    tablemodel = getSearchModel("City");
                    table.setModel(tablemodel);
                }
                else if(comBox.getSelectedItem().equals("户型")){
                    tablemodel = getSearchModel("Taox");
                    table.setModel(tablemodel);
                }
                else if(comBox.getSelectedItem().equals("房屋类型")){
                    tablemodel = getSearchModel("housetype");
                    table.setModel(tablemodel);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == b5){
            try {
                rush();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == b6){
            ChooseHouse();
        }
    }
}
