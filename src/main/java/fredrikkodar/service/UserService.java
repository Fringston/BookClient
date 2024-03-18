package fredrikkodar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fredrikkodar.model.LoginResponse;
import fredrikkodar.model.Role;
import fredrikkodar.model.User;
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
import java.util.Map;

import static fredrikkodar.service.UtilService.*;

public class UserService {

    private static final CloseableHttpClient httpClient = HttpClients.createDefault();

    public static void register() throws IOException, ParseException {
            String username = getStringInput("Enter username: ");
            String password = getPasswordInput("Enter your password: ");

            HttpPost request = new HttpPost("http://localhost:8081/auth/register");
            ObjectMapper mapper = new ObjectMapper();

            // Construct JSON payload manually
            JsonNode payload = mapper.createObjectNode()
                    .put("username", username)
                    .put("password", password);

            // Serialize JSON payload to string
            String jsonString = mapper.writeValueAsString(payload);

            // Set JSON payload as request entity
            HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            request.setEntity(entity);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    System.out.println("User registered successfully");
                } else {
                    System.out.println("Error: " + statusCode);
                    System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
                }
            }
        }

    public static LoginResponse login() throws IOException, ParseException {
        String username = getStringInput("Enter username ");
        String password = getPasswordInput("Enter your password ");

        HttpPost request = new HttpPost("http://localhost:8081/auth/login");
        ObjectMapper mapper = new ObjectMapper();

        // Construct JSON payload manually
        JsonNode payload = mapper.createObjectNode()
                .put("username", username)
                .put("password", password);

        // Serialize JSON payload to string
        String jsonString = mapper.writeValueAsString(payload);

        // Set JSON payload as request entity
        HttpEntity entity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        // Execute the request
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                HttpEntity responseEntity = response.getEntity();
                LoginResponse loginResponse = mapper.readValue(EntityUtils.toString(responseEntity), new TypeReference<LoginResponse>() {});
                //System.out.println("Login successful!");
                return loginResponse;
            } else {
                System.out.println("Error: " + statusCode);
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
                return null;
            }
        }
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

                return mapper.readValue(EntityUtils.toString(entity), new TypeReference<ArrayList<User>>() {
                });

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
        }
        return null;
    }

    // Clara
    // Testar denna metod till AdminMenu.java
    public static void deleteUser(String jwt, Long id) {
        try {
            HttpDelete request = new HttpDelete("http://localhost:8081/users/" + id);
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == 200) {
                    System.out.println("User deleted successfully");
                } else if (statusCode == 404) {
                    System.out.println("User not found");
                } else {
                    System.out.println("Error: " + statusCode);
                }
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void deleteAccount(String jwt) {
        try {
            HttpDelete request = new HttpDelete("http://localhost:8081/users/me");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

            try (CloseableHttpResponse response = httpClient.execute(request)) {

                if (response.getCode() != 200) {
                    System.out.println("Something went wrong with the request: " + response.getCode());
                    return;
                }
                System.out.println("User deleted successfully");

            } catch (JsonProcessingException e) {
                System.out.println("Json Processing Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void changePassword (String jwt, String oldPassword, String newPassword, String confirmPassword) {
        try {
            ChangingPassword changingPassword = new ChangingPassword(oldPassword, newPassword, confirmPassword);
            HttpPut request = new HttpPut("http://localhost:8081/users/changePass");
            request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
            request.setEntity(createPayload(changingPassword));
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() != 200) {
                    System.out.println("Something went wrong with the request: " + response.getCode());
                    return;
                }
                System.out.println("Password changed successfully");
            } catch (JsonProcessingException e) {
                System.out.println("Json Processing Error: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void changeRole(String jwt, Long id, Role role) throws IOException, ParseException {
        HttpPut request = new HttpPut("http://localhost:8081/users/" + id);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);

        // Create JSON payload manually
        ObjectMapper mapper = new ObjectMapper();
        JsonNode payload = mapper.createObjectNode()
                .put("role", role.getAuthority());

        // Serialize JSON payload to string
        String jsonPayload = mapper.writeValueAsString(payload);

        // Set JSON payload as request entity
        HttpEntity entity = new StringEntity(jsonPayload, ContentType.APPLICATION_JSON);
        request.setEntity(entity);

        // Execute the request and handle the response
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int statusCode = response.getCode();
            if (statusCode == 200) {
                System.out.println("Role changed successfully");
            } else {
                System.out.println("Error: " + statusCode);
                System.out.println("Response Body: " + EntityUtils.toString(response.getEntity()));
            }
        }
    }


}