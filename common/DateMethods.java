/* By Agus */
package common;

import java.text.SimpleDateFormat;
import java.util.Scanner;

import static utils.ScreenMethods.cleanScreen;


public class DateMethods {

    public static java.util.Date enterDate() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        boolean dateIsCorrect = false;
        java.util.Date date = new java.util.Date();
        do {
            System.out.print("Ingrese la fecha en el formato dd-mm-aaaa: ");
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
