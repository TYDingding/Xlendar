import xlendarFrame.*;
import javax.swing.*;
import java.sql.*;

public class XlendarMainClass {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        }catch (Exception e){
            e.getMessage();
        }

        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Xlendar\\lib\\xlendar.db2");
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS event");
            statement.executeUpdate("CREATE TABLE event(PRIMARY KEY(eventId) , date CHAR(20) , time CHAR(20) ," +
                    " eventName CHAR(40))");

        }catch (SQLException e){
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

        XlendarFrame xlendarFrame = new XlendarFrame();
        xlendarFrame.setBounds(200,200,1000,600);
        xlendarFrame.setTitle("Xlendar");
        xlendarFrame.setLocationRelativeTo(null);//窗体居中显示
        xlendarFrame.setVisible(true);
        xlendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
