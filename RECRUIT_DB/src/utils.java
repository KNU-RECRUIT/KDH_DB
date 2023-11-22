import java.util.Scanner;

public class utils {
    public static final int WIN = 0;
    public static final int UNIX = 1;
    public static final String base_url = "jdbc:oracle:thin:@localhost:1521:";
    public static final String orcl = "orcl";
    public static final String xe = "xe";
    public static final String UNIVERSITY = "university";
    public static final String RECRUIT = "recruit";
    public static final String COMP322 = "comp322";
    public static final String worst = "worst";

    public static void title(){
        // https://patorjk.com/software/taag/#p=display&f=Graffiti&t=RECRUIT
        System.out.println(
        "________________________________________ ____ ___.______________\n" 
        + "\\______   \\_   _____/\\_   ___ \\______   \\    |   \\   \\__    ___/\n"
        + " |       _/|    __)_ /    \\  \\/|       _/    |   /   | |    |   \n"
        + " |    |   \\|        \\\\     \\___|    |   \\    |  /|   | |    |   \n"
        + " |____|_  /_______  / \\______  /____|_  /______/ |___| |____|   \n"
        + "        \\/        \\/         \\/       \\/                        ");                 
    }

    public static int queryOption1(int OS, Scanner sc){
        boolean looptrigger = true;
        int option = 0;
        while(looptrigger){
            utils.resetConsole(OS);
            utils.title();
            System.out.println("");
            System.out.println("select option : ");
            System.out.println("1. query1");
            System.out.println("2. query2");
            System.out.println("3. query3");
            System.out.println("4. query4");
            System.out.println("5. query5");
            System.out.printf(">_ ");
            option = sc.nextInt();
            if(1<= option && option <= 5){
                looptrigger = false;
            }
            else{
                System.out.println("Wrong selection, input again");
            }
        }
        return option;
    }

    public static int queryOption2(int OS, Scanner sc){
        boolean looptrigger = true;
        int option = 0;
        while(looptrigger){
            utils.resetConsole(OS);
            utils.title();
            System.out.println("");
            System.out.println("select option : ");
            System.out.println("1. Search whole announcements with selected ID");
            System.out.println("2. Searching for posts made by employees at the department head level, based on certain conditions");
            System.out.println("3. Delete the announcement posts by POST_ID written by me.");
            System.out.println("4. exit program");
            System.out.printf(">_ ");
            option = sc.nextInt();
            if(1<= option && option <= 4){
                looptrigger = false;
            }
            else{
                System.out.println("Wrong selection, input again");
            }
        }
        return option;
    }

    public static int queryOption3(int OS, Scanner sc){
        boolean looptrigger = true;
        int option = 0;
        while(looptrigger){
            utils.resetConsole(OS);
            utils.title();
            System.out.println("");
            System.out.println("select option : ");
            System.out.println("1. query1");
            System.out.println("2. query2");
            System.out.println("3. query3");
            System.out.println("4. query4");
            System.out.println("5. query5");
            System.out.printf(">_ ");
            option = sc.nextInt();
            if(1<= option && option <= 5){
                looptrigger = false;
            }
            else{
                System.out.println("Wrong selection, input again");
            }
        }
        return option;
    }

    public static int menu(int OS, Scanner sc){
        boolean looptrigger = true;
        int menu = 0;
        while(looptrigger){
            resetConsole(OS);
            title();
            System.out.println("Welcome "+ test.name + "!");
            System.out.println("");
            System.out.println("select menu : ");
            System.out.println("1. customer management");
            System.out.println("2. announcement management");
            System.out.println("3. user post management");
            System.out.println("4. log out");
            System.out.printf(">_ ");
            menu = sc.nextInt();
            if(1<= menu && menu <= 4){
                looptrigger = false;
            }
            else{
                System.out.println("Wrong selection, input again");
            }
        }
        return menu;
    }

    public static int getOSInfo() {
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            return WIN; // return 0
        } else if (OS.contains("mac") || OS.contains("nix") || OS.contains("nux") || OS.contains("sunos")) {
            return UNIX; // return 1
        } else {
            System.err.println("Wrong OS!");
            return -1;
        }
    }

    public static void resetConsole(int OS) {
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

    public static String setURL(int OS) {
        String URL = "";
        if (OS == WIN) {
            URL = base_url + orcl;
        } else if (OS == UNIX) {
            URL = base_url + xe;
        }
        return URL;
    }
}
