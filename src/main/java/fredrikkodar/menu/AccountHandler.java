package fredrikkodar.menu;

import fredrikkodar.service.UserService;
import fredrikkodar.service.UtilService;

public class AccountHandler {
    private boolean isRunning = true;
    public void accountChoice() {
        String[] accountOptions = {"1. Change password", "2. Delete account", "3. Back to UserMenu\n"};
        for (String option : accountOptions) {
            System.out.println(option);
        }
    }

    public void runAccountMenu() {
        while (isRunning) {
            accountChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice);
        }
    }

    private void userChoice(int choice) {
        // create switch case for user choice
        switch (choice) {
            case 1:
                System.out.println("Change password\n");
                changePassword();
                break;
            case 2:
                System.out.println("Delete account\n");
                deleteAccount();
                break;
            case 3:
                System.out.println("Back to UserMenu\n");
                isRunning = false; // Exit Handle account submenu and return to UserMenu
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }


    private void deleteAccount() {
        UserService.deleteAccount();
    }

    private void changePassword() {
        String newPassword = UtilService.getStringInput("Enter new password: ");
        UserService.changePassword(newPassword);
    }
}
