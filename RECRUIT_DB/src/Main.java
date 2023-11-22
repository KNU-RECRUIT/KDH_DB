// package lab7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Main {
    // public static final String URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String USER_UNIVERSITY ="university";
    public static final String USER_PASSWD ="comp322";
    public static final String TABLE_NAME = "POST_INFO";

    public static void main(String[] args) throws IOException, InterruptedException {
        Connection conn = null; // Connection object
        Statement stmt = null;	// Statement object
        ResultSet rs = null;    // Resultset object
        String sql = ""; // an SQL statement
        try {
            // Load a JDBC driver for Oracle DBMS
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }catch(ClassNotFoundException e) {
            System.err.println("error = " + e.getMessage());
            System.exit(1);
        }

        // Make a connection
        try{
            conn = DriverManager.getConnection(URL, USER_UNIVERSITY, USER_PASSWD);
        }catch(SQLException ex) {
            ex.printStackTrace();
            System.err.println("Cannot get a connection: " + ex.getLocalizedMessage());
            System.err.println("Cannot get a connection: " + ex.getMessage());
            System.exit(1);
        }

        Screen.title();

        // login
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        String departmentId = "";
        String departmentName = "";
        Screen screen = null;

        // check if the user exists
        try {
            sql = "SELECT * FROM EMPLOYEE_INFO" + " WHERE ID = '" + id + "' AND NAME = '" + name + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("인증에 실패하였습니다. 프로그램을 종료합니다.");
                System.exit(1);
            }
            else {
                departmentId = rs.getString(4);
                System.out.println("Welcome " + name + "!");

                Thread.sleep(3000);

                sql = "SELECT NAME FROM DEPARTMENT_INFO " + "WHERE DEPARTMENT_ID = '" + departmentId + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                if (!rs.next()) {
                    System.out.println("사용자 정보를 불러오지 못하였습니다. 프로그램을 종료합니다.");
                    System.exit(1);
                }
                else {
                    departmentName = rs.getString(1);
                    screen = new Screen(name, departmentName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }

        // menu
        screen.menu();
    }
}