package fredrikkodar.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UtilService {

    public static String getStringInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);
        try {

            String input = scan.nextLine();
            if (input.equals("")) {
                System.out.println("Try again");
                return getStringInput(prompt);
            }
            return input;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.\n");
            scan.next(); // discard the invalid input
            return getStringInput(prompt);
        }
    }

    public static int getIntInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);

        try {
            int input = scan.nextInt();

            if (input == 0) {
                System.out.println("Try again");
                return getIntInput(prompt);
            }

            return input;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.\n");
            scan.next(); // discard the invalid input
            return getIntInput(prompt);
        }
    }

    public static Long getLongInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);

        try {
            long input = scan.nextLong();

            if (input == 0) {
                System.out.println("Try again");
                return getLongInput(prompt);
            }

            return input;

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.\n");
            scan.next(); // discard the invalid input
            return getLongInput(prompt);
        }
    }

    public static StringEntity createPayload(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return new StringEntity(mapper.writeValueAsString(object), ContentType.APPLICATION_JSON);
    }

    public static String getPasswordInput(String prompt) {
        Scanner scan = new Scanner(System.in);
        System.out.print(prompt);
        String input = scan.nextLine();

        if (input.equals("")) {
            System.out.println("Try again");
            return getPasswordInput(prompt);
        } else {
            return input;
        }
    }

}
