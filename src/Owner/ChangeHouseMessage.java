package Owner;

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

public class ChangeHouseMessage extends JFrame implements ActionListener {
    private String UserName;
    private JTable table;
    private MyTableModel tablemodel;
    private JButton b2,b3;
    private Connection conn;
    private PreparedStatement ps;
    private int count;
    private ResultSet rs;

    public ChangeHouseMessage(String username) throws SQLException {
        this.UserName = username;
        this.setSize(1200,300);
        this.setTitle("修改房源信息");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tablemodel = getModel();
        table = new JTable(tablemodel);
        table.setPreferredScrollableViewportSize(new Dimension(500,250));
        JScrollPane scroll = new JScrollPane(table);
        this.getContentPane().add(scroll,BorderLayout.CENTER);

        b2 = new JButton();
        b2 = new ChangeIconSize().ChangIconSize(b2,"img\\save.png",15,15,"修改");
        b2.addActionListener(this);
        b3 = new JButton();
        b3 = new ChangeIconSize().ChangIconSize(b3,"img\\delete.png",15,15,"删除");
        b3.setFocusable(false);
        b3.addActionListener(this);

        JToolBar toolBar = new JToolBar();
        toolBar.add(b2);
        toolBar.add(b3);
        this.getContentPane().add(toolBar,BorderLayout.SOUTH);

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


    public MyTableModel getModel() throws SQLException {
        MyTableModel tableModel = new MyTableModel();
        int OID = FindID();
        conn = JdbcUtils.getConnection();
        String sql = "select * from sale where OID = "+ OID;
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery(sql);


        String[] Column = {"房源编号","城市","房屋类型","地段","户型","当前楼层","总楼层","装修","面积(平米)","价格","小区","产权","电话","房产建成日期"};
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
            house.setBd(rs.getString("BD"));
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
                            v.get(i).getBd()
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
            String sql = "update sale set  City = ?, HouseType =?, Diduan = ?, Taox =?, Lc = ?, Tc = ?, Zx = ?, Mianj = ?, " +
                    "Jiag = ?, Xiaoq = ?, Chanq = ?, Tel = ?, Bd = ? where ZID = ? ";
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
                    ps.setInt(14,(Integer) table.getValueAt(index,0));

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
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
    }
}
