package fredrikkodar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fredrikkodar.model.Book;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.EntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookService {
    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static List<Book> getAllBooks(String jwt) throws IOException, ParseException {
       // call the API to get all books
        HttpGet request = new HttpGet("http://localhost:8081/books");
        // add the JWT to the request
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        // execute the request
        CloseableHttpResponse response = httpClient.execute(request);
        // check the response code
        if (response.getCode() != 200){
            System.out.println("Error: " + response.getCode());
            return null;
        }

        // parse the response
        HttpEntity entity = response.getEntity();
        // convert the response to a list of books
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(EntityUtils.toString(entity),new TypeReference<ArrayList<Book>>() {});
    }

    // get a book by id
    public static Book getBookById(Long id, String jwt) throws IOException, ParseException {
        // call the API to get a book by id
        HttpGet request = new HttpGet("http://localhost:8081/books/" + id);
        // add the JWT to the request
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        // execute the request
        CloseableHttpResponse response = httpClient.execute(request);
        // check the response code
        if (response.getCode() != 200){
            System.out.println("Error: " + response.getCode());
            return null;
        }

        // parse the response
        HttpEntity entity = response.getEntity();
        // convert the response to a book
        ObjectMapper mapper = new ObjectMapper();
       Book book = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Book>() {});
       // print the book
        System.out.printf("Id: %d \n Title: %s,\n Author: %s, ISBN: %s\n", book.getId(), book.getTitle(), book.getAuthor());
        return book;
    }
    // save a book
    public static void saveBook(Book book, String jwt) throws IOException, ParseException {
       Book newBook = book;
       HttpPost request = new HttpPost("http://localhost:8081/books");

       ObjectMapper mapper = new ObjectMapper();
       StringEntity payload = new StringEntity(mapper.writeValueAsString(newBook), ContentType.APPLICATION_JSON);

       request.setEntity(payload);
       request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
       CloseableHttpResponse response = httpClient.execute(request);

         if (response.getCode() != 200){
              System.out.println("Error: " + response.getCode());
              return;
         }

         HttpEntity entity = response.getEntity();

         Book responseBook = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Book>() {});
         if (responseBook.getTitle().equals(newBook.getTitle()) && responseBook.getAuthor().equals(newBook.getAuthor())){
             System.out.println("Book saved successfully");
         }else {
             System.out.println("Error saving book");
         }

    }
    // delete a book
    public static void deleteBook(Long id, String jwt) throws IOException {
        HttpDelete request = new HttpDelete("http://localhost:8081/books/" + id);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(request);
        if (response.getCode() != 200){
            System.out.println("Error: " + response.getCode());
            return;
        }
        System.out.println("Book deleted successfully");
    }

    // update a book
    // update a book
    public static void updateBook(Long id, Book book, String jwt) throws IOException, ParseException {
        HttpPut request = new HttpPut("http://localhost:8081/books/" + id);

        // Serialize the book object to JSON
     ObjectMapper mapper = new ObjectMapper();
     StringEntity payload = new StringEntity(mapper.writeValueAsString(book), ContentType.APPLICATION_JSON);
     request.setEntity(payload);
     request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(request);
        if (response.getCode() != 200){
            System.out.println("Error: " + response.getCode());
            return;
        }
        HttpEntity entity = response.getEntity();
        Book responseBook = mapper.readValue(EntityUtils.toString(entity), new TypeReference<Book>() {});
        if (responseBook.getTitle().equals(book.getTitle()) && responseBook.getAuthor().equals(book.getAuthor())){
            System.out.println("Book updated successfully");
        }else {
            System.out.println("Error updating book");
        }

        System.out.println("Book updated successfully");
    }

}
