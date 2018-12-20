package xlendarFrame;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class XlendarFrame extends JFrame implements ActionListener {
//    JLabel labelDay[] = new JLabel[42];
    JTextField  text=new JTextField(10);
    JLabel showMessage=new JLabel("",JLabel.CENTER);
    Calendar getDate = Calendar.getInstance();
    JButton eventButtons[] = new JButton[63];

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    static int eventNumber;

    public XlendarFrame()
    {
        dbConnection();
        setBackground(new Color(249,248,235));
        JPanel pCenter=new JPanel();
        pCenter.setBackground(new Color(249,248,235));

        pCenter.setLayout(new GridLayout(10,8));

        //set head of days in a week
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Time"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Monday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Tuesday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Wednesday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Thursday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Friday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Saturday"));
        pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), "Sunday"));

        //initialize eventButtons
        for(int i = 0; i < 63; i++)
        {
            eventButtons[i] = new JButton("");
            eventNumber = i;
            eventButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String inputEvent = JOptionPane.showInputDialog("Input Event");
                    String inputTime = JOptionPane.showInputDialog("Time");
                    try{
                        statement.executeUpdate("insert into event values("+ eventNumber +", '2018.12.20'," + inputTime + "," + inputEvent);
                    }catch(SQLException e1){
                        System.out.println(e1.getErrorCode());
                    }
                }
            });
        }

        //add eventButtons to grid
        for (int i = 0, j = 0; i < 72; i++)
        {
            if (i%8 == 0){
                j++;
                pCenter.add(showBorder(new BevelBorder(BevelBorder.RAISED, Color.DARK_GRAY, Color.GRAY), String.valueOf(j)));
            }else{
                if (i - j < 63){
                    pCenter.add(eventButtons[i - j]);
                }
            }
        }
        //pCenter.add();

//        for(int i=0;i<42;i++)
//        {
//            labelDay[i]=new JLabel("",JLabel.CENTER);
//            pCenter.add(labelDay[i]);
//        }

        //Show date of today
        JPanel pToday=new JPanel();
        pToday.add(showMessage);
        int year=getDate.get(getDate.YEAR);
        int month=getDate.get(getDate.MONTH)+1;
        int day=getDate.get(getDate.DAY_OF_MONTH);
        showMessage.setText("Today :" + year + "." + month + "." + day);
        showMessage.setFont(new java.awt.Font("宋体",1,18));
        getContentPane().add(pToday,BorderLayout.NORTH);

        getContentPane().add(pCenter);
//       ScrollPane scrollPane=new ScrollPane();
//       scrollPane.add(pCenter);
//       getContentPane().add(scrollPane,BorderLayout.CENTER);

        try {
            if (rs != null) {
                rs.close();
            }
            if (connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e);
        }
    }



    public void dbConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            e.getMessage();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:D:\\Xlendar\\lib\\xlendar.db2");
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS event");
            statement.executeUpdate("CREATE TABLE event(eventId string , date string , time string ," +
                    " eventName string)");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    JPanel showBorder(Border b, String borderName) {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        String mm = borderName;
        mm = mm.substring(mm.lastIndexOf('.') + 1);
        jp.add(new JLabel(mm, JLabel.CENTER), BorderLayout.CENTER);
        jp.setBorder(b);
        return jp;
    }

    public void actionPerformed(ActionEvent e){

    }
}
