package fredrikkodar.menu;

import fredrikkodar.model.LoginResponse;
import fredrikkodar.model.Role;
import fredrikkodar.model.User;
import fredrikkodar.service.UtilService;
import fredrikkodar.service.UserService;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class UserMenu {

    private boolean isRunning = true;

    public void userMenuChoice() {
        String[] userMenuOptions = {"1. Library", "2 Authors", "3. Handle account", "4. Admin\n"};
        for (String option : userMenuOptions) {
            System.out.println(option);
        }
    }

    public void runUserMenu(String jwt) throws IOException, ParseException {
        while (isRunning) {
            userMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userMenuChoice(choice, jwt);
        }
    }

    private void userMenuChoice(int choice, String jwt) throws IOException, ParseException {
        switch (choice) {
            case 1:
               System.out.println("Library\n");
    //            BookMenu bookMenu = new BookMenu();
     //          bookMenu.runBookMenu(jwt);
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
                LoginResponse loginResponse = UserService.login();
                if (isAdmin(loginResponse.getUser())) {
                    AdminMenu adminMenu = new AdminMenu();
                    adminMenu.runAdminMenu(loginResponse.getToken());
                } else {
                    System.out.println("Access denied. Only admins can access the admin menu.");
                }
                break;


            default:
                System.out.println("Invalid choice\n");
        }
    }

    private static boolean isAdmin(User user) {
        if (user != null && user.getAuthorities() != null) {
            for (Role role : user.getAuthorities()) {
                if ("admin".equalsIgnoreCase(role.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException, ParseException {
        UserMenu userMenu = new UserMenu();
        userMenu.runUserMenu("jwt");
    }
}
