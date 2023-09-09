/* By Agus */
package common;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import static utils.ScreenMethods.cleanScreen;


public class DateMethods {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static java.util.Date enterDate(String message) {
        Scanner scanner = new Scanner(System.in);
        boolean dateIsCorrect = false;
        java.util.Date date = new java.util.Date();
        do {
            System.out.print(message);
            String dateString = scanner.next();
            try {
                date = dateFormat.parse(dateString);
                dateIsCorrect = true;
            } catch (Exception e) {
                cleanScreen();
                System.out.println("El formato de la fecha es incorrecto.");
            }
        } while (!dateIsCorrect);
        return date;
    }
}
