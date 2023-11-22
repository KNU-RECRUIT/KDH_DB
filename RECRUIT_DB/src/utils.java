public class utils {
    public static final int WIN = 0;
    public static final int UNIX = 0;
    public static final String orcl = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String xe = "jdbc:oracle:thin:@localhost:1521:xe";

    public static void menu1(){
        System.out.println(">  1.고객 관리");
        System.out.println("   2.공지글 관리");
        System.out.println("   3.고객 작성글 관리");
        System.out.println("   4.로그아웃");
    }

    public static void menu2(){
        System.out.println("   1.고객 관리");
        System.out.println(">  2.공지글 관리");
        System.out.println("   3.고객 작성글 관리");
        System.out.println("   4.로그아웃");
    }

    public static void menu3(){
        System.out.println("   1.고객 관리");
        System.out.println("   2.공지글 관리");
        System.out.println(">  3.고객 작성글 관리");
        System.out.println("   4.로그아웃");
    }

    public static void menu4(){
        System.out.println("   1.고객 관리");
        System.out.println("   2.공지글 관리");
        System.out.println("   3.고객 작성글 관리");
        System.out.println(">  4.로그아웃");
    }

    public static int getOSInfo() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            return WIN;
        } else if (OS.contains("mac") || OS.contains("nix") || OS.contains("nux") || OS.contains("sunos")) {
            return UNIX;
        } else {
            System.err.println("Wrong OS!");
            return -1;
        }
    }

    public static String setURL(int OS){
        if(OS == WIN){

        }
        else if(OS == UNIX){

        }
    }
}
