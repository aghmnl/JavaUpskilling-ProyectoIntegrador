/* By Agus */
package common;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class ScreenMethods {

    static Scanner scanner = new Scanner(System.in); // Esto es para que el usuario pueda ingresar la información por consola

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
        int number = 0;

        do {
            try {
                number = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Ingreso incorrecto. Debe ingresar un número. Por favor vuelva a intentar:");
                scanner.nextLine(); // Consume el ingreso inválido. Esto asegura que el objeto scanner esté listo para leer el siguiente input del usuario.
            }
        } while (number == 0);

        return number;
    }

    public static String enterYorN() {
        String chosenOption = scanner.next().toUpperCase();
        while (!Objects.equals(chosenOption, "S") && !Objects.equals(chosenOption, "N")) {
            System.out.println("Respuesta incorrecta, por favor elegir entre S (Sí) y N (No)");
            chosenOption = scanner.next().toUpperCase();
        }

        return chosenOption;
    }
}
