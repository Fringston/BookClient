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


    private static void deleteAccount(String jwt) {
        UserService.deleteAccount(jwt);
    }

    private static void changePassword(String jwt) {
        String newPassword = UtilService.getStringInput("Enter new password: ");
        String confirmPassword = UtilService.getStringInput("Confirm new password: ");
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match\n");
            return;
        }
        String oldPassword = UtilService.getStringInput("Enter old password: ");
        UserService.changePassword(jwt,oldPassword, newPassword, confirmPassword);
    }

    public static void main(String[] args) {
        changePassword("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDUxNTAzOCwiZXhwIjoxNzEwNjAxNDM4fQ.ZERbekDUm4DYA2aPD18f0q3xHj9TrJxa7SX1_7n-NTw");


    }
}
