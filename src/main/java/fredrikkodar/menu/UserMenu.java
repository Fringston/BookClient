package fredrikkodar.menu;

import fredrikkodar.model.Book;
import fredrikkodar.service.UtilService;

public class UserMenu {

    private boolean isRunning = true;

    public void userMenuChoice() {
        String[] userMenuOptions = {"1. Library", "2 Authors", "3. Handle account", "4. Admin\n"};
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
//                BookMenu bookMenu = new BookMenu();
//                bookMenu.runBookMenu(jwt);
            case 2:
                System.out.println("Authors\n");
                AuthorMenu authorMenu = new AuthorMenu();
                authorMenu.runAuthorMenu(jwt);
                break;
            case 3:
                System.out.println("Handle account\n");
                AccountHandler accountHandler = new AccountHandler();
                accountHandler.runAccountMenu(jwt);
                break;
            case 4:
                System.out.println("Admin\n");
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.runAdminMenu(jwt);
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }
}
