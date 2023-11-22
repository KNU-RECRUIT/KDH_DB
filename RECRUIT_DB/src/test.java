import java.sql.*;
import java.util.*;

public class test {
    private static int OS;
    private static String URL = "";
    private static String ID = "";
    public static String name = "";
    private static String SQL = "";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static void main(String[] args) {
        utils.title();
        Scanner sc = new Scanner(System.in);
        initialize();
        conn = makeConnection(conn);
        try {
            stmt = conn.createStatement();
        } catch (SQLException se) {
            se.printStackTrace();
        }

        logIn(sc);

        int menu = utils.menu(OS, sc);
        menuCall(menu);

        closeConnection(conn, stmt);
    }

    // load menu based on user selection
    public static void menuCall(int menu) {
        switch (menu) {
            case 1: {
                System.out.println("customer management selected");
                customerManagement(conn, stmt);
                break;
            }
            case 2: {
                System.out.println("announcement management selected");
                announcementManagement(conn, stmt);
                break;
            }
            case 3: {
                System.out.println("user post management selected");
                userPostManagement(conn, stmt);
                break;
            }
            case 4: {
                System.out.println("log out selected.");
                System.out.println("Exit program");
                System.exit(0);
                break;
            }
            default: {
                System.err.println("Wrong selection!");
                System.exit(1);
                break;
            }

        }
    }

    public static void customerManagement(Connection conn, Statement stmt){
        // System.out.println("customerManagement works!");
    }

    public static void announcementManagement(Connection conn, Statement stmt){
        // System.out.println("announcementManagement works@");
        String sql = "SELECT * FROM POST_INFO WHERE POST_ID IN (SELECT POST_ID FROM ANNOUNCEMENT_INFO WHERE MANAGER_ID = '"+ID+"') AND TYPE = 'A'";

        // check if the user exists
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("인증에 실패하였습니다. 프로그램을 종료합니다.");
                System.exit(1);
            }
            else {
                System.out.printf("%-12s|%-10s|%-10s|%-5s|\n", "POST ID", "Posted", "Updated", "Views");
                System.out.println("------------+----------+----------+-----|");
                while(rs.next())
                {
                    String postID = rs.getString(1);
                    java.util.Date postedDate = rs.getDate(2);
                    java.util.Date updateDate = rs.getDate(3);
                    int views = rs.getInt(4);


                    System.out.print(postID);
                    System.out.print("|");
                    System.out.print(postedDate);
                    System.out.print("|");
                    System.out.print(updateDate);
                    System.out.print("|");
                    System.out.print(views);
                    System.out.println("|");

                }
                System.out.println("------------+----------+----------+-----|");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }


    }

    public static void userPostManagement(Connection conn, Statement stmt){
        // System.out.println("userPostManagement works#");
    }

    // set values based on OS
    public static void initialize() {
        OS = utils.getOSInfo();
        URL = utils.setURL(OS);
        System.out.println("OS and URL setting completed");
    }

    // whole process of sign in and verification
    public static void logIn(Scanner sc) {
        inputAccount(sc);
        verifyAccount(ID, name);
    }

    // input user ID and name to log in
    public static void inputAccount(Scanner sc) {
        System.out.printf("Input your ID : ");
        ID = sc.nextLine();
        System.out.printf("Input your name : ");
        name = sc.nextLine();
    }

    // verify if ID and name is valid
    public static void verifyAccount(String ID, String name) {
        String departmentID = "";
        String departmentName = "";
        // String name = sc.nextLine();
        try {
            // SQL = "SELECT * FROM EMPLOYEE_INFO";
            SQL = "SELECT * "
            + "FROM EMPLOYEE_INFO "
            + "WHERE ID = '" + ID + "' AND NAME = '" + name + "'";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(SQL);
            if (!rs.next()) {
                System.out.println("Failed to verification.");
                System.out.println("Exit program");
                System.exit(1);
            } else {
                departmentID = rs.getString(4);
                System.out.println("Welcome " + name + "!");
                // System.out.println("Welcome My master!");

                // Thread.sleep(3000);

                SQL = "SELECT NAME FROM DEPARTMENT_INFO " + "WHERE DEPARTMENT_ID = '" + departmentID + "'";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(SQL);
                if (!rs.next()) {
                    System.out.println("Failed to load user info");
                    System.out.println("Exit program");
                    System.exit(1);
                } else {
                    departmentName = rs.getString(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    // connect to Database recruit: recruit/worst
    public static Connection makeConnection(Connection conn) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load a JDBC driver for Oracle DBMS
            System.out.println("Driver Loading: Success!"); // Get a Connection object
        } catch (ClassNotFoundException cnfe) {
            System.err.println("error = " + cnfe.getMessage());
            System.exit(1);
        }

        // Make a connection
        try {
            conn = DriverManager.getConnection(URL, utils.RECRUIT, utils.worst);
            System.out.println("Oracle Connected.");
        } catch (SQLException se) {
            se.printStackTrace();
            System.err.println("Cannot get a connection: " + se.getLocalizedMessage());
            System.err.println("Cannot get a connection: " + se.getMessage());
            System.exit(1);
        }
        return conn;
    }

    // close the connection
    public static void closeConnection(Connection conn, Statement stmt) {
        // Release database resources.
        try {
            stmt.close(); // Close the Statement object.
            System.out.println("stmt successfully closed");
            conn.close();// Close the Connection object.
            System.out.println("conn successfully closed");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}