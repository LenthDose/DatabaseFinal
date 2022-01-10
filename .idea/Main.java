public class Main extends JFrame implements ActionListener {


    public Main() {
        this.setSize(600,300);
        this.setTitle("房产中介管理系统");
        this.setLocationRelativeTo(getOwner());
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setLayout(new GridLayout(5,1));
        JButton house = new JButton("房源信息管理");
        this.getContentPane().add(house);
        house.addActionListener(this);
        JButton owner = new JButton("业主信息管理");
        this.getContentPane().add(owner);
        owner.addActionListener(this);
        JButton customer = new JButton("客户信息管理");
        this.getContentPane().add(customer);
        customer.addActionListener(this);
        JButton search = new JButton("交易信息管理");
        this.getContentPane().add(search);
        search.addActionListener(this);
        JButton recycle = new JButton("所属房源");
        this.getContentPane().add(recycle);
        recycle.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("房源信息管理")){
            try {
                new House();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("业主信息管理")){
            try {
                new Owner();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("客户信息管理")){
            try {
                new Customer();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

}
