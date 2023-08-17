package datos;

import java.util.Scanner;

import static menu.Menu.categories;
import static utils.ScreenMethods.cleanScreen;


public class Category {

    static Scanner scanner = new Scanner(System.in);
    public static boolean thisIsACategory(String category) {
        return categories.contains(category);
    }

    public static void listCategories() {
        int i = 1;
        for (String category : categories) {
            System.out.println(i + ". " + category);
            i++;
        }
    }

    public static String enterCategory() {
        boolean categoryIsCorrect = false;
        int categoryNumber;
        do {
            System.out.println("Seleccione la categoría entre las siguientes opciones: ");
            listCategories();
            categoryNumber = scanner.nextInt();
            if ((categoryNumber < 1) || (categoryNumber > categories.size())) {
                cleanScreen();
                System.out.println("La categoría seleccionada es incorrecta.");
            } else {
                categoryIsCorrect = true;
            }
        } while(!categoryIsCorrect);
        return categories.get(categoryNumber - 1);
    }


}
