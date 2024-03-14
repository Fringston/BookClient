package fredrikkodar.menu;

import fredrikkodar.service.UtilService;

public class UserMenu {

    private boolean isRunning = true;


    private boolean isRunning = true;

    public void userMenuChoice() {
        String[] userMenuOptions = {"1. Library", "2. Handle account", "3. Logout\n"};
        for (String option : userMenuOptions) {
            System.out.println(option);
        }
    }

    public void runUserMenu(String jwt) {
        while (isRunning) {
            userMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userMenuChoice(choice, jwt);
        }
    }

    private void userMenuChoice(int choice, String jwt) {
        switch (choice) {
            case 1:
                System.out.println("Library\n");

                break;
            case 2:
                System.out.println("Handle account\n");
                AccountHandler accountHandler = new AccountHandler();
                accountHandler.runAccountMenu();
                break;
            case 3:
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.runAdminMenu(jwt);
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }
}
