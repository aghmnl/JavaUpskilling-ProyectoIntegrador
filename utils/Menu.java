/* By Agus */
package utils;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.impl.CategoryDAOImpl;
import dao.impl.ExpenseDAOImpl;

import java.util.Scanner;

import static common.ScreenMethods.cleanScreen;
import static common.ScreenMethods.enterNumber;

public class Menu {
    ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    CategoryDAO categoryDAO = new CategoryDAOImpl();


    static Scanner scanner = new Scanner(System.in); // Esto es para que el usuario pueda ingresar la información por consola


    public void optionsMenu() {

        int option;

        do {
            System.out.println();
            System.out.println("Ingrese la opción deseada: ");
            System.out.println("   1. Ingresar un gasto.");
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   2. Ver todos los gastos.");
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   3. Ver gastos por categoría.");
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   4. Ver gastos por período.");
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   5. Editar gasto.");
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   6. Eliminar gasto.");
            if(!categoryDAO.getAll().isEmpty()) System.out.println("   7. Gestionar categorías.");
            System.out.println("   8. Salir de la aplicación.");
            option = enterNumber();

            switch (option) {
                case 1 -> expenseDAO.addExpense();
                case 2 -> expenseDAO.showAll();
                case 3 -> expenseDAO.findExpensesByCategory();
                case 4 -> submenuTimeFiltering();
                case 5 -> expenseDAO.editExpense();
                case 6 -> expenseDAO.deleteExpense();
                case 7 -> submenuEditCategory();
                case 8 -> {
                    System.out.println();
                    System.out.println("Gracias por utilizar el gestor de gastos!!");
                    System.out.println("Made by Agus.");
                }
                default -> { cleanScreen(); System.out.println("La opción ingresada no es válida");}
            }
        } while (option != 8);

        scanner.close();   // This is to close the scanner. It should only be done at the very end of the code.
    }

    private void submenuEditCategory() {
        int option;

        do {
            cleanScreen();
            System.out.println("Ingrese la opción deseada: ");
            System.out.println("   1. Ver todas las categorías.");
            System.out.println("   2. Agregar categoría.");
            System.out.println("   3. Modificar categoría.");
            System.out.println("   4. Eliminar categoría.");
            System.out.println("   5. Ir al menú anterior.");
            option = enterNumber();

            switch (option) {
                case 1 -> categoryDAO.showAll();
                case 2 -> categoryDAO.addCategory();
                case 3 -> categoryDAO.editCategory();
                case 4 -> categoryDAO.deleteCategory();
                case 5 -> System.out.println();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option != 5);

    }

    private void submenuTimeFiltering() {
        int option;

        do {
            cleanScreen();
            System.out.println("Ingrese el periodo de búsqueda: ");
            System.out.println("   1. Por mes.");
            System.out.println("   2. Por año.");
            System.out.println("   3. Por fecha de inicio y fin.");
            System.out.println("   4. Por fecha específica.");
            System.out.println("   5. Ir al menú anterior.");
            option = enterNumber();

            switch (option) {
                case 1 -> expenseDAO.showExpensesByPeriod("MM");
                case 2 -> expenseDAO.showExpensesByPeriod("YYYY");
                case 3 -> expenseDAO.showExpensesByDates();
                case 4 -> expenseDAO.findExpensesByDate();
                case 5 -> System.out.println();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option != 5);
    }

}
