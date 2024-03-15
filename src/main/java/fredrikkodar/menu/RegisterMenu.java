package fredrikkodar.menu;

import fredrikkodar.service.UserService;
import fredrikkodar.service.UtilService;

public class RegisterMenu {
    public static void register() {
        UserService.register();
    }

    public static void runRegisterMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. Register\n2. Back to Main Menu\n");
            int choice = UtilService.getIntInput("Enter choice: ");
            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice\n");
            }
        }
    }
    public static void main(String[] args) {
        runRegisterMenu();
    }
}
