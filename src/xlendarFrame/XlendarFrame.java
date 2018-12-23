package xlendarFrame;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
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
    static String today;
    String todayOfWeek;
    String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    int week;
    JButton nextMonth;
    JButton previousMonth;

    static int eventNumber[] = new int[63];

    Socket client = null;
    String serverAddr = "localhost";
    int serverPort = 8888;
    PrintWriter out;

    File localRecordFile;

    public XlendarFrame(int week)
    {
        //Connect to Server
        this.week = week;
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

        //Create local file to record events
        localRecordFile = new File("c:"+File.separator+"Xlendar"+File.separator+"localRecordFile.txt") ;        // 实例化File类的对象
        if(localRecordFile.exists()){
        }else{
            try{
                localRecordFile.createNewFile() ;        // 创建文件，根据给定的路径创建
            }catch(IOException e){
                e.printStackTrace() ;    // 输出异常信息
            }
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

        try {
            if (client == null) {
                for (int i = 0; i < 63; i++) {
                        eventButtons[i] = new JButton("");
                        eventNumber[i] = i;
                    buttonActionPerformed(eventButtons[i], i, this.week);
                }

                try{
                    FileInputStream fis = new FileInputStream(localRecordFile);
                    //Construct BufferedReader from InputStreamReader
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                    String line = null;
                    String[] sourceStrArray = null;
                    while ((line = br.readLine()) != null) {
                        sourceStrArray = line.split("'");
                        int t1 = Integer.parseInt(sourceStrArray[1]);
                        int t2 = Integer.parseInt(sourceStrArray[3]);
                        if(t1 == this.week){
                            eventButtons[t2].setText(sourceStrArray[7]+sourceStrArray[9]);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }else{

            }
        }catch (Exception e){
            e.printStackTrace();
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

        nextMonth = new JButton("下周");
        previousMonth = new JButton("上周");
        nextMonth.addActionListener(this);
        previousMonth.addActionListener(this);

        //Show date of today
        JPanel pToday=new JPanel();
        pToday.add(showMessage);
        int year=getDate.get(getDate.YEAR);
        int month=getDate.get(getDate.MONTH)+1;
        int day=getDate.get(getDate.DAY_OF_MONTH);
        today = year + "." + month + "." + day;
        int w = getDate.get(Calendar.DAY_OF_WEEK) - 1;
        if(w < 0)w = 0;
        todayOfWeek = weekDays[w];
        this.week = week;
        showMessage.setText("Today :" +today + "   " + todayOfWeek + "          week:" + week);
        showMessage.setFont(new java.awt.Font("宋体",1,18));

        pToday.add(nextMonth);
        pToday.add(previousMonth);
        getContentPane().add(pToday,BorderLayout.NORTH);

        getContentPane().add(pCenter);
       ScrollPane scrollPane=new ScrollPane();
       scrollPane.add(pCenter);
       getContentPane().add(scrollPane,BorderLayout.CENTER);

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

    public void buttonActionPerformed(JButton button, int i, int w){
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream outPut = null;
                try {
                    outPut = new FileOutputStream(localRecordFile,true);
                    OutputStreamWriter writer = new OutputStreamWriter(outPut, "gbk");

                    String inputEvent = JOptionPane.showInputDialog("Input Event");
                    String inputTime = JOptionPane.showInputDialog("Time");
                    button.setText(inputTime + "\n" + inputEvent);
                    String message = "insert into event values('"+ w + "','" + i + "','" + today + "','" + inputTime + "','" + inputEvent + "')";

                    writer.append(message);
                    writer.append("\r\n");
                    writer.close();

                    out.println(message);
                    out.flush();

                }catch(Exception e2){
                    e2.printStackTrace();
                }finally {
                    try {
                        if(outPut != null){
                            outPut.close();
                        }
                    }catch (IOException e3){
                        e3.printStackTrace();
                    }
                }

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
        if(e.getSource() == nextMonth){
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            XlendarFrame xlendarFrame = new XlendarFrame(this.week+1);
            xlendarFrame.setBounds(200,200,1000,600);
            xlendarFrame.setTitle("Xlendar");
            xlendarFrame.setLocationRelativeTo(null);//窗体居中显示
            xlendarFrame.setVisible(true);
            xlendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else if(e.getSource()==previousMonth){
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            XlendarFrame xlendarFrame = new XlendarFrame(this.week-1);
            xlendarFrame.setBounds(200,200,1000,600);
            xlendarFrame.setTitle("Xlendar");
            xlendarFrame.setLocationRelativeTo(null);//窗体居中显示
            xlendarFrame.setVisible(true);
            xlendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
