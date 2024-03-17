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

    public void updateBook2(String jwt) throws IOException, ParseException {
        Long bookId = UtilService.getLongInput("Enter book id to update: ");
        Book existingBook = BookService.getBookById(bookId, jwt); // Fetch the existing book
        if (existingBook == null) {
            System.out.println("Book not found");
            return;
        }

        String newTitle = UtilService.getStringInput("Enter new book title: ");
        Long newAuthorId = UtilService.getLongInput("Enter new author id: ");

        existingBook.setTitle(newTitle);
        Author newAuthor = new Author();
        newAuthor.setId(newAuthorId);
        existingBook.setAuthor(newAuthor);

        // Extract authorId from existingBook and pass it to updateBook
        Long authorId = existingBook.getAuthor().getId();
        BookService.updateBook(bookId, existingBook, authorId, jwt);
    }

    public void updateBook(String jwt) throws IOException, ParseException {
        Long bookId = UtilService.getLongInput("Enter book id to update: ");
        Book existingBook = BookService.getBookById(bookId, jwt); // Fetch the existing book
        if (existingBook == null) {
            System.out.println("Book not found");
            return;
        }

        System.out.println("1. Update book title");
        System.out.println("2. Update book author");
        System.out.println("3. Update both title and author");
        System.out.println("4. No update");
        int choice = UtilService.getIntInput("Enter your choice: ");

        switch (choice) {
            case 1:
                String newTitle = UtilService.getStringInput("Enter new book title: ");
                existingBook.setTitle(newTitle);
                break;
            case 2:
                Long newAuthorId = UtilService.getLongInput("Enter new author id: ");
                Author newAuthor = new Author();
                newAuthor.setId(newAuthorId);
                existingBook.setAuthor(newAuthor);
                break;
            case 3:
                String updatedTitle = UtilService.getStringInput("Enter new book title: ");
                Long updatedAuthorId = UtilService.getLongInput("Enter new author id: ");
                existingBook.setTitle(updatedTitle);
                Author updatedAuthor = new Author();
                updatedAuthor.setId(updatedAuthorId);
                existingBook.setAuthor(updatedAuthor);
                break;
            case 4:
                System.out.println("No updates made.");
                return;
            default:
                System.out.println("Invalid choice");
                return;
        }

        // Extract authorId from existingBook and pass it to updateBook
        Long authorId = existingBook.getAuthor().getId();
        BookService.updateBook(bookId, existingBook, authorId, jwt);
    }

public static void main(String[] args) throws IOException, ParseException {
        BookMenu bookMenu = new BookMenu();
        bookMenu.runBookMenu("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDY2MzE5MywiZXhwIjoxNzEwNzQ5NTkzfQ.OQX5ti5oANBjSYshGZXegGr7MbuhyB0GS31cbId2gX4");

    }
}
