package fredrikkodar.menu;

import fredrikkodar.service.UtilService;

public class AdminMenu {

    private boolean isRunning = true;

    public void adminMenuChoice() {
        String[] adminMenuOptions = {"1. Get all users", "2. Delete user", "3. Change role", "4. Back to Login Menu\n"};
        for (String option : adminMenuOptions) {
            System.out.println(option);
        }
    }

    public void runAdminMenu(String jwt) {
        while (isRunning) {
            adminMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            adminMenuChoice(choice, jwt);
        }
    }

    private void adminMenuChoice(int choice, String jwt) {
        switch (choice) {
            case 1:
                System.out.println("Get all users\n");
                // anropa UserService för att hämta alla användare
                break;
            case 2:
                System.out.println("Delete user\n");
                // anropa UserService för att radera användare
                break;
            case 3:
                System.out.println("Change role\n");
                // anropa UserService för att ändra roll
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }
}
