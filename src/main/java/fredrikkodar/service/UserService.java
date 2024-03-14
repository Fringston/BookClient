package fredrikkodar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fredrikkodar.model.LoginResponse;
import fredrikkodar.model.User;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static fredrikkodar.service.UtilService.*;

public class UserService {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void register() {

        try {
            String username = getStringInput("Enter username: ");
            String password = getPasswordInput("Enter your password: ");

            User newUser = new User(0L, username, password);

            HttpPost request = new HttpPost("http://localhost:8081/auth/register");
            request.setEntity(createPayload(newUser));

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                if (response.getCode() != 200) {
                    System.out.println("Something went wrong with the request: " + response.getCode());
                    return;
                }

                HttpEntity payload = response.getEntity();

                ObjectMapper mapper = new ObjectMapper();

                User responseUser = mapper.readValue(EntityUtils.toString(payload), new TypeReference<User>() {});

                System.out.printf("User %s has been created with the user-id: %d%n", responseUser.getUsername(), responseUser.getId());

            } catch (JsonProcessingException e) {
                System.out.println("Json Processing Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            } catch (ParseException e) {
                System.out.println("Parse Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static LoginResponse login(){

        try {
            String username = getStringInput("Enter username ");
            String password = getPasswordInput("Enter your password ");

            User loginUser = new User(0L, username, password);

            HttpPost request = new HttpPost("http://localhost:8081/auth/login");
            request.setEntity(createPayload(loginUser));

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                if (response.getCode() != 200) {
                    System.out.println("Something went wrong with the request: " + response.getCode());
                    return null;
                }

                HttpEntity payload = response.getEntity();

                ObjectMapper mapper = new ObjectMapper();
                LoginResponse loginResponse = mapper.readValue(EntityUtils.toString(payload), new TypeReference<LoginResponse>() {});
                if (loginResponse.getUser() == null) {
                    System.out.println("Wrong username or password. Please try again.");
                    return null;
                }
                System.out.printf("\nUser: %s has logged in%n", loginResponse.getUser().getUsername());

                return loginResponse;

            } catch (JsonProcessingException e) {
                System.out.println("Json Processing Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Exception Error: " + e.getMessage());
            } catch (ParseException e) {
                System.out.println("Parse Error: " + e.getMessage());
            } return null;

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } return null;
    }

    public static List<User> getUsers(String jwt) {

        try {
            HttpGet request = new HttpGet("http://localhost:8081/users/all");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                if (response.getCode() != 200) {
                    System.out.println("Something went wrong with the request: " + response.getCode());
                    return null;
                }

                HttpEntity entity = response.getEntity();

                ObjectMapper mapper = new ObjectMapper();

                return mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<User>>() {});

            } catch (JsonMappingException e) {
                System.out.println("Json Mapping Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Io Error: " + e.getMessage());
            } catch (ParseException e) {
                System.out.println("Parse Error: " + e.getMessage());
            }
            return null;

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } return null;
    }

}
