import xlendarFrame.*;
import javax.swing.*;
import java.sql.*;
import java.util.Scanner;

public class XlendarMainClass {
    public static void main(String[] args) {
        System.out.print("输入行政周数:");
        Scanner scan = new Scanner(System.in);
        int read = scan.nextInt();

        XlendarFrame xlendarFrame = new XlendarFrame(read);
        xlendarFrame.setBounds(200,200,1000,600);
        xlendarFrame.setTitle("Xlendar");
        xlendarFrame.setLocationRelativeTo(null);//窗体居中显示
        xlendarFrame.setVisible(true);
        xlendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
