package Manager;

import Tool.ChangeIconSize;
import Tool.JdbcUtils;
import Tool.MyTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class HouseControl extends JFrame implements ActionListener {
    private JTable table;
    private MyTableModel tablemodel;
    private JButton b1,b2,b3,b4,b5;
    private JTextField search;
    private JComboBox comBox;
    private Connection conn;
    private PreparedStatement ps;
    private int count;
    private ResultSet rs;
    private String No;

    public HouseControl(String no) throws SQLException {
        this.No = no;
        this.setSize(1200,300);
        this.setTitle("房源信息");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        this.getContentPane().add(scroll,BorderLayout.CENTER);

        b1 = new JButton();
        b1 = new ChangeIconSize().ChangIconSize(b1,"img\\add.png",15,15,"添加");
        b1.addActionListener(this);
        b2 = new JButton();
        b2 = new ChangeIconSize().ChangIconSize(b2,"img\\save.png",15,15,"修改");
        b2.addActionListener(this);
        b3 = new JButton();
        b3 = new ChangeIconSize().ChangIconSize(b3,"img\\delete.png",15,15,"删除");
        b3.setFocusable(false);
        b3.addActionListener(this);
        b3.setHorizontalTextPosition(SwingConstants.CENTER);
        b3.setVerticalTextPosition(SwingConstants.BOTTOM);
        b4 = new JButton();
        b4 = new ChangeIconSize().ChangIconSize(b4,"img\\search.png",15,15,"搜索");
        b4.addActionListener(this);
        search = new JTextField();
        b5 = new JButton();
        b5 = new ChangeIconSize().ChangIconSize(b4,"img\\refresh.png",15,15,"刷新");
        b5.addActionListener(this);
        String[] items = {"楼盘","户型布局","价格"};
        comBox = new JComboBox(items);

        JToolBar toolBar = new JToolBar();
        toolBar.add(b1);
        toolBar.add(b2);
        toolBar.add(b3);
        toolBar.add(b5);
        JToolBar toolBar1 = new JToolBar();
        toolBar1.add(comBox);
        toolBar1.add(search);
        toolBar1.add(b4);
        this.getContentPane().add(toolBar,BorderLayout.SOUTH);
        this.getContentPane().add(toolBar1,BorderLayout.NORTH);
        this.setVisible(true);
    }

    public MyTableModel getModel() throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        conn = JdbcUtils.getConnection();
        String sql = "select * from sale where no = "+ No ;
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);


        String[] Column = {"房源编号","用户名","城市","房屋类型","地段","户型","当前楼层","总楼层","装修","面积(平米)","价格","小区","产权","电话","房源注册日期","房产建成日期","业主","房源状态"};
        int i;
        for (i = 0;i<Column.length;i++){
            tableModel.addColumn(Column[i]);
        }

        ArrayList<HouseMessage> v = new ArrayList<>();
        while (rs.next()){
            HouseMessage house = new HouseMessage();
            house.setZID(rs.getInt("ZID"));
            house.setUserName(rs.getString("uid"));
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
                            v.get(i).getUserName(),
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


    public void change() throws SQLException {
        int i,index = 0;
        if(table.getCellEditor() != null){
            table.getCellEditor().stopCellEditing();
        }
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update sale set  uid = ?, City = ?, HouseType =?, Diduan = ?, Taox =?, Lc = ?, Tc = ?, Zx = ?, Mianj = ?, " +
                    "Jiag = ?, Xiaoq = ?, Chanq = ?, Tel = ?, Date = ?, Bd = ?, person = ?, situation = ? where ZID = ? and No = " + No;
            ps = conn.prepareStatement(sql);
            count = tablemodel.getEditedIndex().size();
            if(count > 0){
                for (i = 0;  i<count ; i++) {
                    index = tablemodel.getEditedIndex().get(i);
                    ps.setString(1,(String)table.getValueAt(index,1));
                    ps.setString(2,(String)table.getValueAt(index,2));
                    ps.setString(3,(String)table.getValueAt(index,3));
                    ps.setString(4,(String) table.getValueAt(index,4));
                    ps.setString(5,(String) table.getValueAt(index,5));
                    ps.setString(6,(String)table.getValueAt(index,6));
                    ps.setString(7,(String) table.getValueAt(index,7));
                    ps.setString(8,(String) table.getValueAt(index,8));
                    ps.setString(9,(String) table.getValueAt(index,9));
                    ps.setString(10,(String) table.getValueAt(index,10));
                    ps.setString(11,(String) table.getValueAt(index,11));
                    ps.setString(12,(String) table.getValueAt(index,12));
                    ps.setString(13,(String) table.getValueAt(index,13));
                    ps.setString(14,(String) table.getValueAt(index,14));
                    ps.setString(15,(String) table.getValueAt(index,15));
                    ps.setString(16,(String) table.getValueAt(index,16));
                    ps.setString(17,(String) table.getValueAt(index,17));
                    ps.setInt(18,(Integer) table.getValueAt(index,0));
                    ps.addBatch();
                }
            }
            int[] c = ps.executeBatch();
            if(c!=null){
                JOptionPane.showMessageDialog(this,"修改成功","提示",JOptionPane.OK_OPTION);
            }
            else {
                JOptionPane.showMessageDialog(this,"修改失败","提示",JOptionPane.ERROR_MESSAGE);

            }
            tablemodel.clearEditedIndex();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps);
        }

    }

    public void delete() throws SQLException {
        conn = JdbcUtils.getConnection();
        try{
            if(table.getSelectedRows().length > 0){
                int[] selRowIndexs = table.getSelectedRows();
                String sql = "delete from sale where ZID = ?";
                ps = conn.prepareStatement(sql);
                for (int i = 0; i < selRowIndexs.length; i++) {
                    ps.setString(1,table.getValueAt(selRowIndexs[i],0).toString());
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
        catch (SQLException e){
            e.getStackTrace();
        }
        finally {
            JdbcUtils.close(conn,ps);
        }
    }

    public void rush() throws SQLException {
        tablemodel = getModel();
        table.setModel(tablemodel);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == b2){
            try {
                change();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getSource() == b3){
            try {
                delete();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else if(e.getSource() == b5){
            try {
                rush();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }

    }
}