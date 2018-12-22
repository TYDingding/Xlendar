package xlendarFrame;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Calendar;

public class XlendarFrame extends JFrame implements ActionListener {
//    JLabel labelDay[] = new JLabel[42];
    JTextField  text=new JTextField(10);
    JLabel showMessage=new JLabel("",JLabel.CENTER);
    Calendar getDate = Calendar.getInstance();
    JButton eventButtons[] = new JButton[63];
    String today;

    Connection connection = null;
    Statement statement = null;
    ResultSet rs = null;
    static int eventNumber[] = new int[63];

    Socket client = null;
    String serverAddr = "localhost";
    int serverPort = 8888;
    PrintWriter out;

    public XlendarFrame()
    {

        try {
            client = new Socket(serverAddr, serverPort);
            System.out.println("Client: " + client);
            out = new PrintWriter(client.getOutputStream());
            out.println("Hello");
            out.flush();  // need to flush a short message
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        //dbConnection();

//        try {
//            Class.forName("org.sqlite.JDBC");
//        }catch (Exception e){
//            e.getMessage();
//        }
//
//        try {
//            connection = DriverManager.getConnection("jdbc:sqlite:D:\\Xlendar\\lib\\xlendar.db2");
//            statement = connection.createStatement();
//
//            statement.executeUpdate("DROP TABLE IF EXISTS event");
//            statement.executeUpdate("CREATE TABLE event(eventId integer , date string , time string ," +
//                    " eventName string)");
//
            //initialize eventButtons
            for(int i = 0; i < 63; i++)
            {
                eventButtons[i] = new JButton("");
                eventNumber[i] = i;
                buttonActionPerformed(eventButtons[i], eventNumber[i]);
            }

//        }catch (SQLException e){
//            System.err.println(e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


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

        //Show date of today
        JPanel pToday=new JPanel();
        pToday.add(showMessage);
        int year=getDate.get(getDate.YEAR);
        int month=getDate.get(getDate.MONTH)+1;
        int day=getDate.get(getDate.DAY_OF_MONTH);
        today = year + "." + month + "." + day;
        showMessage.setText("Today :" +today);
        showMessage.setFont(new java.awt.Font("宋体",1,18));
        getContentPane().add(pToday,BorderLayout.NORTH);

        getContentPane().add(pCenter);
//       ScrollPane scrollPane=new ScrollPane();
//       scrollPane.add(pCenter);
//       getContentPane().add(scrollPane,BorderLayout.CENTER);

    }


    public  void dbConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            e.getMessage();
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:D:\\Xlendar\\lib\\xlendar.db2");
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS event");
            statement.executeUpdate("CREATE TABLE event(eventId integer , date string , time string ," +
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

    public void buttonActionPerformed(JButton button, int i){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputEvent = JOptionPane.showInputDialog("Input Event");
                String inputTime = JOptionPane.showInputDialog("Time");
                button.setText(inputTime + "\n" + inputEvent);
                String message = "insert into event values('"+ i +"','" + today + "','" + inputTime + "','" + inputEvent + "')";

                out.println(message);
                out.flush();

//                try{
//                    if(connection.isClosed()){
//                        System.out.println("Connection is closed");
//                    }
//                    statement.executeUpdate("insert into event values("+ i +",'" + today + "','" + inputTime + "','" + inputEvent + "')");
//                }catch(SQLException e1){
//                    System.out.println(e1.getErrorCode());

 //               }
            }
        });
    }

    public void actionPerformed(ActionEvent e){

    }
}
