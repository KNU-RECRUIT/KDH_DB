public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        
        Screen.title();

        Screen Sc = new Screen("My name", "My deptName");

        Sc.resetConsole();

        // System.out.println(Sc.getName());
        // System.out.println(Sc.getDepartmentName());

        Sc.menu();
    }
}
