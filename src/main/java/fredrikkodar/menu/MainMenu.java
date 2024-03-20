package fredrikkodar.menu;

import fredrikkodar.service.UtilService;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class MainMenu {

    private static boolean isRunning = true;
    public void printMenu() {
        String[] menyOptions = {"1. Log in", "2. Register", "3. Exit\n"};
        System.out.println("Welcome to the library!\n");
        for (String option : menyOptions) {
            System.out.println(option);
        }
    }
    public void runMenu() throws IOException, ParseException {
        while (isRunning){
            printMenu();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice);
        }
    }

    private void userChoice(int choice) throws IOException, ParseException {


        // crate swith case for runMenu


        // crate swith case for runMenu

        switch (choice){
            case 1:
                Login loginMenu = new Login();
                loginMenu.runLoginMenu();
                break;
            case 2:
                System.out.println("Register\n");
                RegisterMenu.runRegisterMenu();
                break;
            case 3:
                System.out.println("Goodbye!\n");
                isRunning = false;
                System.exit(0);
            default:
                System.out.println("Invalid choice\n");
        }
    }
    public static void main(String[] args) throws IOException, ParseException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.runMenu();
    }
}
