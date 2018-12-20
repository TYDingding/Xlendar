import xlendarFrame.*;
import javax.swing.*;
import java.sql.*;

public class XlendarMainClass {
    public static void main(String[] args) {
        XlendarFrame xlendarFrame = new XlendarFrame();
        xlendarFrame.setBounds(200,200,1000,600);
        xlendarFrame.setTitle("Xlendar");
        xlendarFrame.setLocationRelativeTo(null);//窗体居中显示
        xlendarFrame.setVisible(true);
        xlendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
