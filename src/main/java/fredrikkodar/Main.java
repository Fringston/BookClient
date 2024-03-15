package fredrikkodar;

import fredrikkodar.menu.MainMenu;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {


        MainMenu mainMenu = new MainMenu();
        mainMenu.runMenu();
    }
}