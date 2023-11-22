import java.sql.*;
import java.sql.Date;
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
        menuCall(menu, sc);

        closeConnection(conn, stmt);
        sc.close();
    }

    // load menu based on user selection
    public static void menuCall(int menu, Scanner sc) {
        switch (menu) {
            case 1: {
                System.out.println("customer management selected");
                customerManagement(conn, stmt);
                break;
            }
            case 2: {
                System.out.println("announcement management selected");
                announcementManagement(conn, stmt, sc);
                break;
            }
            case 3: {
                System.out.println("user post management selected");
                userPostManagement(conn, stmt, sc);
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

    public static void customerManagement(Connection conn, Statement stmt) {
        // System.out.println("customerManagement works!");
    }

    public static void announcementManagement(Connection conn, Statement stmt, Scanner sc) {
        int option = utils.queryOption2(OS, sc);
        String SQL2 = "";
        sc.nextLine(); // flsuh buffer
        switch (option) {
            case 1: {
                String MANAGER_ID = sc.nextLine();
                SQL2 = "SELECT * "
                        + "FROM POST_INFO "
                        + "WHERE POST_ID IN "
                        + "(SELECT POST_ID "
                        + "FROM ANNOUNCEMENT_INFO "
                        + "WHERE MANAGER_ID = '" + MANAGER_ID + "') AND TYPE = 'A'";

                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(SQL2);
                    System.out.printf("%-12s|%-10s|%-10s|%-5s|\n", "POST ID", "Posted", "Updated", "Views");
                    System.out.println("------------+----------+----------+-----|");
                    while (rs.next()) {
                        String postID = rs.getString(1);
                        Date postedDate = rs.getDate(2);
                        Date updateDate = rs.getDate(3);
                        int views = rs.getInt(4);

                        System.out.print(postID);
                        System.out.print("|");
                        System.out.print(postedDate);
                        System.out.print("|");
                        System.out.print(updateDate);
                        System.out.print("|");
                        System.out.printf("%5d", views);
                        System.out.println("|");
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
                break;
            }
            case 2: {
                System.out.println("=====[공지를 관리할 수 있는 부서 관리자급 직원 검색]=====");
                System.out.println("=검색 옵션 선택=");
                System.out.println("1. 이름으로 검색");
                System.out.println("2. 직원 부서로 검색");
                System.out.println("3. 전체 검색");
                System.out.print("> ");
                int a2_option = sc.nextInt();
                if(a2_option==1)
                {
                    System.out.print("직원 이름 입력: ");
                    String a2_name = sc.next();
                    SQL2 = "SELECT DISTINCT E.NAME, D.CONTACT, D.DEPARTMENT_ID FROM EMPLOYEE_INFO E, DEPARTMENT_INFO D, ANNOUNCEMENT_INFO A WHERE E.ID = D.HEAD_ID AND A.MANAGER_ID = E.ID AND E.NAME='"+a2_name+"'";
                }
                else if(a2_option==2)
                {
                    System.out.print("직원 부서 입력: ");
                    String a2_id = sc.next();
                    SQL2 = "SELECT DISTINCT E.NAME, D.CONTACT, D.DEPARTMENT_ID FROM EMPLOYEE_INFO E, DEPARTMENT_INFO D, ANNOUNCEMENT_INFO A WHERE E.ID = D.HEAD_ID AND A.MANAGER_ID = E.ID AND D.DEPARTMENT_ID='"+a2_id+"'";
                }
                else if(a2_option==3)
                {
                    SQL2 = "SELECT DISTINCT E.NAME, D.CONTACT, D.DEPARTMENT_ID FROM EMPLOYEE_INFO E, DEPARTMENT_INFO D, ANNOUNCEMENT_INFO A WHERE E.ID = D.HEAD_ID AND A.MANAGER_ID = E.ID";
                }

                // check if the user exists
                try {
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(SQL2);
                        System.out.printf("%-4s|%-10s|%-4s|\n","Name", "Phone", "Dept");
                        System.out.println("=========================");

                        while(rs.next())
                        {
                            String Name = rs.getString(1);
                            String Phone = rs.getString(2);
                            String Department = rs.getString(3);

                            System.out.print(Name);
                            System.out.print("|");
                            System.out.print(Phone);
                            System.out.print("|");
                            System.out.print(Department);
                            System.out.println("|");

                        }
                        System.out.println("=========================");
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
                break;
            }
            case 3: {

                System.out.println("Input the POST_ID you want to delete");
                String a3_pid = sc.next();
                String SQL4 = "DELETE FROM ANNOUNCEMENT_INFO WHERE MANAGER_ID='"+ID+"' AND POST_ID='"+a3_pid+"'";


                try {
                    stmt = conn.createStatement();
                    int cnt = stmt.executeUpdate(SQL4);
                    if(cnt>=1)
                    {
                        String dsql = "DELETE FROM POST_INFO WHERE POST_ID='"+a3_pid+"'";
                        String dsql2 = "DELETE FROM USER_POST_INFO WHERE POST_ID='"+a3_pid+"'";
                        stmt.executeUpdate(dsql);
                        stmt.executeUpdate(dsql2);
                    }

                    System.out.println(cnt+" rows deleted.");
                    System.out.println("=========================");
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }

                break;
            }
            case 4: {
                System.out.println("exit program");
                System.exit(0);
                break;
            }
            default: {
                System.err.println("Wrong option!");
                System.exit(1);
                break;
            }
        }

    }

    public static void userPostManagement(Connection conn, Statement stmt, Scanner sc) {
        // System.out.println("userPostManagement works#");

        int option = utils.queryOption4(OS, sc);
        sc.nextLine(); // flsuh buffer
        switch (option) {
            case 1: {

                try {
                    System.out.println("Please select option: (min or max)");
                    System.out.println("1. Max(Post Count)");
                    System.out.println("2. Min(Post Count)");
                    int minmaxopt = sc.nextInt();
                    String minmaxqueryparam;

                    if(minmaxopt==1)
                    {
                        minmaxqueryparam = "MAX";
                    }
                    else if(minmaxopt==2)
                    {
                        minmaxqueryparam = "MIN";
                    }
                    else{
                        System.err.println("Wrong Option!");
                        break;
                    }
                    String SQLF = "SELECT C.NAME AS CUSTOMER_NAME, U.AUTHOR_ID, COUNT(U.POST_ID) AS POST_COUNT FROM CUSTOMER_INFO C JOIN USER_POST_INFO U ON C.ID = U.AUTHOR_ID GROUP BY C.NAME, U.AUTHOR_ID HAVING COUNT(U.POST_ID) = (SELECT "+minmaxqueryparam+"(POST_COUNT) FROM (SELECT AUTHOR_ID, COUNT(POST_ID) AS POST_COUNT FROM USER_POST_INFO GROUP BY AUTHOR_ID))";

                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(SQLF);
                    System.out.printf("Customer Name| Author ID|Post Count|\n");
                    System.out.println("=====================");
                    while (rs.next()) {
                        String CNAME = rs.getString(1);
                        String AID = rs.getString(2);
                        int PCNT = rs.getInt(3);


                        System.out.print(CNAME);
                        System.out.print("|");
                        System.out.print(AID);
                        System.out.print("|");
                        System.out.print(PCNT);
                        System.out.println("|");
                        System.out.println("=====================");
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
                break;
            }
            case 2: {

                try {
                    System.out.println("Please select option: (Earliest or Latest)");
                    System.out.println("1. Earliest");
                    System.out.println("2. Latest");
                    int elopt = sc.nextInt();
                    String elparam;

                    if(elopt==1)
                    {
                        elparam = "MIN";
                    }
                    else if(elopt==2)
                    {
                        elparam = "MAX";
                    }
                    else{
                        System.err.println("Wrong Option!");
                        break;
                    }
                    String SQLT="SELECT NAME, GENDER, EMAIL, ID FROM CUSTOMER_INFO WHERE ID IN (SELECT AUTHOR_ID FROM USER_POST_INFO WHERE START_DATE = (SELECT "+elparam+"(START_DATE) FROM USER_POST_INFO)) ORDER BY NAME ASC, ID ASC, GENDER DESC";

                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(SQLT);
                    System.out.printf("Name| Gender|Email|ID\n");
                    System.out.println("=====================");
                    while (rs.next()) {
                        String CNAME = rs.getString(1);
                        String Gender = rs.getString(2);
                        String Email = rs.getString(3);
                        String EID = rs.getString(4);


                        System.out.print(CNAME);
                        System.out.print("|");
                        System.out.print(Gender);
                        System.out.print("|");
                        System.out.print(Email);
                        System.out.println("|");
                        System.out.print(EID);
                        System.out.println("|");
                        System.out.println("=====================");
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
                break;
            }
            case 3: {

                try {
                    System.out.println("Please select option: (Earliest or Latest)");
                    System.out.println("1. Earliest");
                    System.out.println("2. Latest");
                    int qiopt = sc.nextInt();
                    String qiparam;

                    if(qiopt==1)
                    {
                        qiparam = "ASC";
                    }
                    else if(qiopt==2)
                    {
                        qiparam = "DESC";
                    }
                    else{
                        System.err.println("Wrong Option!");
                        break;
                    }
                    String SQLK="SELECT STATUS, POST_ID, LASTLY_UPDATED FROM (SELECT U.STATUS, P.POST_ID, P.LASTLY_UPDATED, ROW_NUMBER() OVER(PARTITION BY U.STATUS ORDER BY P.LASTLY_UPDATED "+qiparam+") AS RN FROM USER_POST_INFO U JOIN POST_INFO P ON U.POST_ID = P.POST_ID) WHERE RN = 1";

                    stmt = conn.createStatement();
                    rs = stmt.executeQuery(SQLK);
                    System.out.printf("Name| Gender|Email|ID\n");
                    System.out.println("=====================");
                    while (rs.next()) {
                        String CNAME = rs.getString(1);
                        String Gender = rs.getString(2);
                        String Email = rs.getString(3);
                        String EID = rs.getString(4);


                        System.out.print(CNAME);
                        System.out.print("|");
                        System.out.print(Gender);
                        System.out.print("|");
                        System.out.print(Email);
                        System.out.println("|");
                        System.out.print(EID);
                        System.out.println("|");
                        System.out.println("=====================");
                    }
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                    System.exit(1);
                }
                break;

            }


        }
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
        // ID = sc.nextLine();
        ID = "UC7358";
        System.out.printf("Input your name : ");
        // name = sc.nextLine();
        name = "김명석";
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
            conn = DriverManager.getConnection(URL, utils.UNIVERSITY, utils.COMP322);
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
