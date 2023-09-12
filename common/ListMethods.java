/* By Agus */
package common;

import java.util.Collection;
import java.util.List;

import static common.ScreenMethods.cleanScreen;
import static common.ScreenMethods.enterNumber;

public class ListMethods {
    public static <T> void printList(Collection<T> list) {
        int i = 1;
        for (T item : list) {
            System.out.println(i + ". " + item);
        i++;
        }
    }

    public static <T> int selectFromList(List<T> list, String message) {
        boolean selectionIsCorrect = false;
        int optionSelected;

        do {
            System.out.println(message);
            printList(list);
            optionSelected = enterNumber();
            if ((optionSelected < 1) || (optionSelected > list.size())) {
                cleanScreen();
                System.out.println("La opción seleccionada es incorrecta.");
            } else {
                selectionIsCorrect = true;
            }
        } while(!selectionIsCorrect);

        return optionSelected;
    }

    public static <T> T selectTFromList(List<T> listT, String message) {
        int optionSelected = selectFromList(listT, message);
        return listT.get(optionSelected - 1);
    }
}
