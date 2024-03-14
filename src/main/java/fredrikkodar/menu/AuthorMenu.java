package fredrikkodar.menu;

import fredrikkodar.service.UtilService;

public class AuthorMenu {

    private boolean isRunning = true;

    public void authorMenuChoice() {
        String[] authorMenuOptions = {"1. Show all authors", "2. Get all books by an author", "3. Search for author", "4. Add new author", "5. Update existing author", "5. Delete author", "6. Back to UserMenu\n"};
        for (String option : authorMenuOptions) {
            System.out.println(option);
        }
    }

    public void runAuthorMenu(String jwt) {
        while (isRunning) {
            authorMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice);
        }
    }

    public void userChoice(int choice) {
        // create switch case for user choice
        switch (choice) {
            case 1:
                System.out.println("Show all authors\n");
                // anropa AuthorService för att visa alla författare
                break;
            case 2:
                System.out.println("Get all books by an author\n");
                // anropa AuthorService för att visa alla böcker av en författare
                break;
            case 3:
                System.out.println("Search for author\n");
                // anropa AuthorService för att söka efter författare
                break;
            case 4:
                System.out.println("Add new author\n");
                // anropa AuthorService för att lägga till ny författare
                break;
            case 5:
                System.out.println("Update existing author\n");
                // anropa AuthorService för att uppdatera författare
                break;
            case 6:
                System.out.println("Delete author\n");
                // anropa AuthorService för att radera författare
                break;
            case 7:
                System.out.println("Back to UserMenu\n");
                isRunning = false; // Exit Handle author submenu and return to UserMenu
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }


}
