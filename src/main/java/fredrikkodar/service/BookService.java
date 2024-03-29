package fredrikkodar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fredrikkodar.model.Book;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
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
        HttpGet request = new HttpGet("http://finalserver-env.eba-mvm2fjyr.eu-north-1.elasticbeanstalk.com/books");
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
        HttpGet request = new HttpGet("http://finalserver-env.eba-mvm2fjyr.eu-north-1.elasticbeanstalk.com/books/" + id);
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
        System.out.printf("Id: %d \n Title: %s,\n Author: %s \n", book.getId(), book.getTitle(), book.getAuthor());
        return book;
    }

    // save a book
    public static void saveBook(Book book, String jwt) throws IOException, ParseException {
        HttpPost request = new HttpPost("http://finalserver-env.eba-mvm2fjyr.eu-north-1.elasticbeanstalk.com/books");
        ObjectMapper mapper = new ObjectMapper();
        // Construct JSON payload manually
        JsonNode payload = mapper.createObjectNode()
                .put("title", book.getTitle())
                .put("authorId", book.getAuthor().getId());

        // Serialize JSON payload to string
        String jsonString = mapper.writeValueAsString(payload);

        // Set JSON payload as request entity
        HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        // Set authorization header
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        // Execute the request
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                System.out.println("Book saved successfully");
            } else {
                System.out.println("Error: " + statusCode);
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
            }
        }
    }


    // delete a book
    public static void deleteBook(Long id, String jwt) throws IOException {
        HttpDelete request = new HttpDelete("http://finalserver-env.eba-mvm2fjyr.eu-north-1.elasticbeanstalk.com/books/" + id);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        CloseableHttpResponse response = httpClient.execute(request);
        if (response.getCode() != 200){
            System.out.println("Error: " + response.getCode());
            return;
        }
        System.out.println("Book deleted successfully");
    }

    // update a book
    public static void updateBook(Long id, Book book, Long authorId, String jwt) throws IOException, ParseException {
        HttpPut request = new HttpPut("http://finalserver-env.eba-mvm2fjyr.eu-north-1.elasticbeanstalk.com/books");
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        ObjectMapper mapper = new ObjectMapper();
        // Construct JSON payload manually
        JsonNode payload = mapper.createObjectNode()
                .put("bookId", id)
                .put("title", book.getTitle())
                .put("authorId", authorId);

        // Serialize JSON payload to string
        String jsonString = mapper.writeValueAsString(payload);

        // Set JSON payload as request entity
        HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        // Execute the request
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                System.out.println("Book updated successfully");
            } else {
                System.out.println("Error: " + statusCode);
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
            }
        }

    }
}
