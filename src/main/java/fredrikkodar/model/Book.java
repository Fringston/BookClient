package fredrikkodar.model;

import lombok.Data;

@Data
public class Book {
    private Long id;
    private String title;
    private Author author;

    public Book() {
    }

    public Book(Long id, String title, Author author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
    public Book(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Author: " + author.getName() + "\n" +
                "-------------------------";
    }
}
