/* By Agus */
package common;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ScreenMethods {
    public static void cleanScreen() {
        // The objective of this method is to clean the screen, but is not achieving its objective
//        System.out.print("\033[H\033[2J");
//        System.out.println("\u001b[2J");
//        System.out.flush();
        System.out.println();
//        try {
//            Runtime.getRuntime().exec("clear");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public static int enterNumber() {
        Scanner scanner = new Scanner(System.in); // Esto es para que el usuario pueda ingresar la información por consola
        int number = 0;

        do {
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ingreso incorrecto. Debe ingresar un número. Por favor vuelva a intentar:");
                scanner.nextLine(); // consume el ingreso inválido. Esto asegura que el objeto scanner esté listo para leer el siguiente input del usuario.
            }
        } while (number == 0);

        return number;
    }
}
