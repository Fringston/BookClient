package fredrikkodar.menu;

import fredrikkodar.model.LoginResponse;
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

    public void runAccountMenu(String jwt) {
        while (isRunning) {
            accountChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice, jwt);
        }
    }

    private void userChoice(int choice, String jwt) {
        // create switch case for user choice
        switch (choice) {
            case 1:
                System.out.println("Change password\n");
                changePassword(jwt);
                break;
            case 2:
                System.out.println("Delete account\n");
                deleteAccount(jwt);
                break;
            case 3:
                System.out.println("Back to UserMenu\n");
                isRunning = false; // Exit Handle account submenu and return to UserMenu
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }


    private void deleteAccount(String jwt) {
        UserService.deleteAccount(jwt);
    }

    private void changePassword(String jwt) {
        String newPassword = UtilService.getStringInput("Enter new password: ");
        String confirmPassword = UtilService.getStringInput("Confirm new password: ");
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match\n");
            return;
        }
        String oldPassword = UtilService.getStringInput("Enter old password: ");
        UserService.changePassword(jwt,newPassword, oldPassword, confirmPassword);
    }
}
