
// package lab7;
// import java.awt.event.*;
import java.util.EventListener;

public class Screen {
    private String name;
    private String departmentName;
    private static final int WIN = 0;
    private static final int UNIX = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Screen(String name, String department_name) {
        this.name = name;
        this.departmentName = department_name;
    }

    public static void title2() {
        // https://patorjk.com/software/taag/#p=display&f=Graffiti&t=RECRUIT
        System.out.println(
                "__________ ___________ _________ __________ ____ ___ .___ ___________\n" +
                        "\\______ \\ \\_ _____/ \\_ ___ \\ \\______ \\ | | \\ | | \\__ ___/\n" +
                        " | _/ | __)_ / \\ \\/ | _/ | | / | | | | \n" +
                        " | | \\ | \\ \\ \\____ | | \\ | | / | | | | \n" +
                        " |____|_ / /_______ / \\______ / |____|_ / |______/ |___| |____| \n" +
                        " \\/ \\/ \\/ \\/ \n" +
                        "----------------------------------------------------------------------------\n");
    }

    public static void title() {
        // error occurs... why?
        // https://patorjk.com/software/taag/#p=display&f=Graffiti&t=RECRUIT
        System.out.println(
        "__________ ___________ _________ __________ ____ ___ .___ ___________\n" +
        "\\______ \\ \\_ _____/ \\_ ___ \\ \\______ \\ | | \\ | | \\__ ___/\n" +
        " | _/ | __)_ / \\ \\/ | _/ | | / | | | | \n" +
        " | | \\ | \\ \\ \\____ | | \\ | | / | | | | \n" +
        " |____|_ / /_______ / \\______ / |____|_ / |______/ |___| |____| \n" +
        " \\/ \\/ \\/ \\/ \n" +
        "----------------------------------------------------------------------------\n");
        // System.out.println("RECRUIT");
    }

    public static void resetConsole() {
        int OS = getOSInfo();
        try {
            if (OS == WIN)
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else if (OS == UNIX)
                new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();

            // System.out.println("Successfully cleared screen");
        } catch (Exception e) {
            System.err.println("Failed to control screen!\n" + e);
            System.exit(1);
        }
    }

    public void menu() {
        resetConsole();

        System.out.println("작업 유형을 선택해주세요.\n");
        // "1. 고객 관리\n" +
        // "2. 공지글 관리\n" +
        // "3. 고객 작성글 관리\n" +
        // "4. 로그아웃");
        System.out.println(">  1.고객 관리");
        System.out.println("   2.공지글 관리");
        System.out.println("   3.고객 작성글 관리");
        System.out.println("   4.로그아웃");
        System.out.println("");
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

    public interface KeyListener extends EventListener {

    }
}