package fredrikkodar.menu;

import fredrikkodar.model.Role;
import fredrikkodar.model.User;
import fredrikkodar.service.UserService;
import fredrikkodar.service.UtilService;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class AdminMenu {

    private boolean isRunning = true;

    public void adminMenuChoice() {
        String[] adminMenuOptions = {"1. Get all users", "2. Delete user", "3. Change role", "4. Back to User Menu\n"};
        for (String option : adminMenuOptions) {
            System.out.println(option);
        }
    }

    public void runAdminMenu(String jwt) throws IOException, ParseException {
        while (isRunning) {
            adminMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            adminMenuChoice(choice, jwt);
        }
    }

    private void adminMenuChoice(int choice, String jwt) throws IOException, ParseException {
        Long id;
        switch (choice) {
            case 1:
                System.out.println("Get all users\n");
                List<User> users = UserService.getUsers(jwt);
                if (users != null) {
                    for (User user : users) {
                        System.out.println(user);
                    }
                } else {
                    System.out.println("Failed to get users");
                }
                break;

            case 2:
                System.out.println("Delete user\n");
                id = UtilService.getLongInput("Enter user id to delete: ");
                UserService.deleteUser(jwt, id);
                break;
            case 3:
                System.out.println("Change role\n");
                 id = UtilService.getLongInput("Enter user id: ");
                String roleInput = UtilService.getStringInput("Enter new role (ROLE_ADMIN, ROLE_USER): ").toUpperCase();
                 Role role = new Role();
                 role.setAuthority(roleInput);
                UserService.changeRole(jwt, id, role);
                break;
           case 4:
                System.out.println("Back to User Menu\n");
                isRunning = false;
                break;

            default:
                System.out.println("Invalid choice\n");
        }
    }
//    public static void main(String[] args) throws IOException {
//        AdminMenu adminMenu = new AdminMenu();
//        adminMenu.runAdminMenu("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDY2NDQ2MiwiZXhwIjoxNzEwNzUwODYyfQ.vX_U5FaGXJvDq0pmZEKk4AanRgh_9pyqtZoDZlxJFHM");
//    }

}
