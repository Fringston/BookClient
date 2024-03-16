package fredrikkodar.menu;

import fredrikkodar.model.Author;
import fredrikkodar.model.Book;
import fredrikkodar.service.AuthorService;
import fredrikkodar.service.BookService;
import fredrikkodar.service.UtilService;

import java.util.List;

public class AuthorMenu {

    private boolean isRunning = true;

    public void authorMenuChoice() {
        String[] authorMenuOptions = {"1. Show all authors", "2. Get all books by an author", "3. Add new author", "4. Update existing author", "5. Delete author", "6. Back to UserMenu\n"};
        for (String option : authorMenuOptions) {
            System.out.println(option);
        }
    }

    public void runAuthorMenu(String jwt) {
        while (isRunning) {
            authorMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice, jwt);
        }
    }

    public void userChoice(int choice, String jwt) {
        // create switch case for user choice
        switch (choice) {
            case 1:
                System.out.println("Show all authors\n");
                List<Author> authors = AuthorService.getAuthors(jwt);
                for (Author author : authors) {
                    System.out.println(author);
                }
                break;
            case 2:
                System.out.println("Show all books by an author\n");
                Long id = UtilService.getLongInput("Enter author id: ");
                List<Book> books = AuthorService.getBooksByAuthor(id, jwt);
                for (Book book : books) {
                    System.out.println(book);
                }
                break;
            case 3:
                System.out.println("Add new author\n");
                addNewAuthor(jwt);
                break;
            case 4:
                System.out.println("Update existing author\n");
                updateAuthor(jwt);
                break;
            case 5:
                System.out.println("Delete author\n");
                deleteAuthor(jwt);
                break;
            case 6:
                System.out.println("Back to UserMenu\n");
                isRunning = false;
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }

    public static void addNewAuthor(String jwt) {
        String name = UtilService.getStringInput("Enter author name: ");
        Author newAuthor = new Author();
        newAuthor.setName(name);
        AuthorService.saveAuthor(newAuthor, jwt);
    }

    public static void updateAuthor(String jwt) {
        Long id = UtilService.getLongInput("Enter author id: ");
        Author author = AuthorService.getAuthorById(id, jwt);
        if (author != null) {
            String name = UtilService.getStringInput("Enter new author name: ");
            author.setName(name);
            AuthorService.updateAuthor(id, author, jwt);
        }
    }

    public static void deleteAuthor(String jwt) {
        Long id = UtilService.getLongInput("Enter author id: ");
        AuthorService.deleteAuthor(id, jwt);
    }


}
