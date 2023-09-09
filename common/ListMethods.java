package common;

import dao.CRUD;

import java.util.Collection;
import java.util.Scanner;

import static utils.ScreenMethods.cleanScreen;

public class ListMethods {
    public static <T> void printList(Collection<T> list) {
        int i = 1;
        for (T item : list) {
            System.out.println(i + ". " + item);
        i++;
        }
    }

    public static <T> int selectFromList(int listSize, String message, CRUD object) {
        Scanner scanner = new Scanner(System.in);
        boolean selectionIsCorrect = false;
        int optionSelected;

        do {
            System.out.println(message);
            object.showAll();
            optionSelected = scanner.nextInt();
            if ((optionSelected < 1) || (optionSelected > listSize)) {
                cleanScreen();
                System.out.println("La opción seleccionada es incorrecta.");
            } else {
                selectionIsCorrect = true;
            }
        } while(!selectionIsCorrect);

        return optionSelected;
        }


}
