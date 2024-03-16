package fredrikkodar.menu;

import fredrikkodar.model.LoginResponse;
import fredrikkodar.service.UserService;
import fredrikkodar.service.UtilService;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class Login {

    private boolean isRunning = true;

    public void loginChoice() {
        String[] loginOptions = {"1. Login", "2. Back to Main Menu\n"};
        for (String option : loginOptions) {
            System.out.println(option);
        }
    }

    public void runLoginMenu() throws IOException, ParseException {
        while (isRunning) {
            loginChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice);
        }
    }

    public void userChoice(int choice) throws IOException, ParseException {
        switch (choice) {
            case 1:
                LoginResponse loginResponse = UserService.login();
                if (loginResponse != null) {
                    System.out.println("Login successful!");
                    //System.out.println("Welcome " + loginResponse.getUser().getUsername());
                    UserMenu userMenu = new UserMenu();
                    userMenu.runUserMenu(loginResponse.getToken());
                } else {
                    System.out.println("Login failed. Please try again.");
                }
                break;
            case 2:
                MainMenu mainMenu = new MainMenu();
                mainMenu.runMenu();
                break;
        }
    }


}





