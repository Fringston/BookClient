package fredrikkodar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fredrikkodar.model.Author;
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
import java.util.List;

public class AuthorService {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static List<Author> getAuthors(String jwt) {
        try {
            // call the API to get all authors
            HttpGet request = new HttpGet("http://localhost:8081/authors");
            // add the JWT to the request
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            // execute the request
            CloseableHttpResponse response = httpClient.execute(request);
            // check the response code
            if (response.getCode() != 200) {
                System.out.println("Error: " + response.getCode());
                return null;
            }

            // parse the response
            HttpEntity entity = response.getEntity();
            // convert the response to a list of authors
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Author>>() {
            });
        } catch (IOException | ParseException e) {
            // log the error and return null or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }


    public static Author getAuthorById(Long id, String jwt) {
        try {
            // call the API to get an author by id
            HttpGet request = new HttpGet("http://localhost:8081/authors/" + id);
            // add the JWT to the request
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            // execute the request
            CloseableHttpResponse response = httpClient.execute(request);
            // check the response code
            if (response.getCode() != 200) {
                System.out.println("Error: " + response.getCode());
                return null;
            }

            // parse the response
            HttpEntity entity = response.getEntity();
            // convert the response to an author
            ObjectMapper mapper = new ObjectMapper();
            Author author = mapper.readValue(EntityUtils.toString(entity), Author.class);
            System.out.printf("Author: %s%n", author);
            return author;
        } catch (IOException | ParseException e) {
            // log the error and return null or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public static void saveAuthor(Author author, String jwt) {
        try {
            Author newAuthor = author;
            HttpPost request = new HttpPost("http://localhost:8081/authors");

            ObjectMapper mapper = new ObjectMapper();
            StringEntity payload = new StringEntity(mapper.writeValueAsString(newAuthor), ContentType.APPLICATION_JSON);

            request.setEntity(payload);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            CloseableHttpResponse response = httpClient.execute(request);

            if (response.getCode() != 200) {
                System.out.println("Error: " + response.getCode());
                return;
            }

            HttpEntity entity = response.getEntity();
            Author responseAuthor = mapper.readValue(EntityUtils.toString(entity), Author.class);

            if (responseAuthor.getName().equals(newAuthor.getName())) {
                System.out.println("Author saved successfully");
            } else {
                System.out.println("Error saving author");
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAuthor(Long id, String jwt) {
        try {
            // call the API to delete an author by id
            HttpDelete request = new HttpDelete("http://localhost:8081/authors/" + id);
            // add the JWT to the request
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            // execute the request
            CloseableHttpResponse response = httpClient.execute(request);
            // check the response code
            if (response.getCode() != 200) {
                System.out.println("Error: " + response.getCode());
                return;
            }
            System.out.println("Author deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateAuthor(Long id, Author author, String jwt) {
        try {
            HttpPut request = new HttpPut("http://localhost:8081/authors");

            // Serialize the author object to JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode payload = mapper.createObjectNode()
                    .put("id", id)
                    .put("name", author.getName()); // Assuming "name" is the property to update

            // Serialize JSON payload to string
            String jsonString = mapper.writeValueAsString(payload);

            // Set JSON payload as request entity
            StringEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            // Set authorization header
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    System.out.println("Author updated successfully");
                } else {
                    System.out.println("Error: " + statusCode);
                    System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static List <Book> getBooksByAuthor(Long id, String jwt) {
        try {
            // call the API to get all books by author id
            HttpGet request = new HttpGet("http://localhost:8081/authors/" + id + "/books");
            // add the JWT to the request
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            // execute the request
            CloseableHttpResponse response = httpClient.execute(request);
            // check the response code
            if (response.getCode() != 200) {
                System.out.println("Error: " + response.getCode());
                return null;
            }

            // parse the response
            HttpEntity entity = response.getEntity();
            // convert the response to a list of books
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(EntityUtils.toString(entity), new TypeReference<List<Book>>() {
            });
        } catch (IOException | ParseException e) {
            // log the error and return null or throw a custom exception
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
//        Author newAuthor = new Author("New Author");
//      updateAuthor(1L, newAuthor, "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxMDYwNjEzMywiZXhwIjoxNzEwNjkyNTMzfQ.1wrV-IzHpLvkc8y6_GHXcTdrGbwbfeZXSY3uWuZ0e9A");
    }


}