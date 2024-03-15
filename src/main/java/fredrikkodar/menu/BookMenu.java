package fredrikkodar.menu;

import fredrikkodar.model.Author;
import fredrikkodar.model.Book;
import fredrikkodar.service.BookService;
import fredrikkodar.service.UtilService;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.List;

public class BookMenu {

    private boolean isRunning = true;

    public void bookMenuChoice() {
        String[] bookMenuOptions = {"1. Show all books", "2. Search for book", "3. Add new book", "4. Update existing book", "5. Delete book", "6. Back to UserMenu\n"};
        for (String option : bookMenuOptions) {
            System.out.println(option);
        }
    }

    public void runBookMenu(String jwt) throws IOException, ParseException {
        while (isRunning) {
            bookMenuChoice();
            int choice = UtilService.getIntInput("Enter choice: ");
            userChoice(choice, jwt);
        }
    }

    public void userChoice(int choice, String jwt) throws IOException, ParseException {
        // create switch case for user choice
        switch (choice) {
            case 1:
                System.out.println("Show all books\n");
                List<Book> books = BookService.getAllBooks(jwt);
                for (Book book : books) {
                    System.out.println(book);
                }
                break;
            case 2:
                System.out.println("Search for book\n");
                BookService.getBookById(UtilService.getLongInput("Enter book id: "), jwt);
                break;
            case 3:
                System.out.println("Add new book\n");
                addNewBook(jwt);
                break;
            case 4:
                System.out.println("Update existing book\n");
                updateBook(jwt);
                break;
            case 5:
                System.out.println("Delete book\n");
                Long deleteBookId = UtilService.getLongInput("Enter book id to delete: ");
                BookService.deleteBook(deleteBookId, jwt);
                break;
            case 6:
                System.out.println("Back to UserMenu\n");
                isRunning = false;
                break;
            default:
                System.out.println("Invalid choice\n");
        }
    }

    public void addNewBook(String jwt) throws IOException, ParseException {
        String title = UtilService.getStringInput("Enter book title: ");
        Long authorId = UtilService.getLongInput("Enter author id: ");
        Book book = new Book();
        book.setTitle(title);
        Author author = new Author();
        author.setId(authorId);
        book.setAuthor(author);
        BookService.saveBook(book, jwt);
    }

    public void updateBook(String jwt) throws IOException, ParseException {
        Long bookId = UtilService.getLongInput("Enter book id to update: ");
        String newTitle = UtilService.getStringInput("Enter new book title: ");
        Long newAuthorId = UtilService.getLongInput("Enter new author id: ");
        Book newBook = new Book();
        newBook.setId(bookId);
        newBook.setTitle(newTitle);
        Author newAuthor = new Author();
        newAuthor.setId(newAuthorId);
        newBook.setAuthor(newAuthor);
        BookService.updateBook(bookId,newBook, jwt);
    }
}
