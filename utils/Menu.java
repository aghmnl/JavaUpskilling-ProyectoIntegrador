/* By Agus */

package utils;

import dao.impl.CategoryDAOImpl;
import entities.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static entities.Expense.*;
import static utils.ScreenMethods.cleanScreen;

public class Menu {
    CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    Expense expenseDao = new Expense();

    public static List<Expense> expenses = new ArrayList<>(); // This is the list of expenses
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Defining the format of the date to be used along the code

    static Scanner scanner = new Scanner(System.in); // This is to let the user enter information in the console

    public void optionsMenu() {

        int option;

        do {
            System.out.println("Ingrese la opción deseada: ");
            System.out.println("   1. Ingresar un gasto.");
            if(!expenses.isEmpty()) System.out.println("   2. Ver, editar o eliminar un gasto.");
            System.out.println("   3. Gestionar categorías.");
            if(!expenses.isEmpty()) System.out.println("   4. Ver gastos por rubro.");
            if(!expenses.isEmpty()) System.out.println("   5. Ver gastos por periodo.");
            System.out.println("   6. Salir de la aplicación.");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> submenuNewExpense();
                case 2 -> submenuFindExpense();
                case 3 -> submenuManageCategories();
                case 4 -> submenuExpensesByCategory();
                case 5 -> submenuExpensesByTime();
                case 6 -> {
                    System.out.println("Gracias por utilizar el gestor de gastos!!");
                    System.out.println("Made by Agus.");
                }
                default -> { cleanScreen(); System.out.println("La opción ingresada no es válida");}
            }
        } while (option != 6);

        scanner.close();   // This is to close the scanner. It should only be done at the very end of the code.
    }

    private void submenuNewExpense() {
        cleanScreen();

        Expense expense = new Expense();

        System.out.print("Ingrese el monto del gasto: ");
        expense.setAmount(scanner.nextFloat());
        System.out.print("Ingrese la descripción del gasto: ");
        String description = scanner.next();
        expense.setDescription(description);
        expense.setCategory(categoryDAO.enterCategory());
        expense.setDate(enterDate());
        System.out.println("El gasto ingresado es el siguiente: ");
        System.out.println(expense);

        expenses.add(expense);
    }

    private void submenuFindExpense() {
        int option;

        do {
            cleanScreen();
            System.out.println("Ingrese el método de búsqueda: ");
            System.out.println("   1. Por descripción.");
            System.out.println("   2. Por monto.");
            System.out.println("   3. Por categoría.");
            System.out.println("   4. Por fecha.");
            System.out.println("   5. Por ID.");
            System.out.println("   6. Listar todos los gastos.");
            System.out.println("   7. Ir al menú anterior.");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> findExpenseByDescription();
                case 2 -> findExpenseByAmount();
                case 3 -> expenseDao.findExpenseByCategory();
                case 4 -> findExpenseByDate();
                case 5 -> findExpenseByID();
                case 6 -> { cleanScreen(); listExpenses(expenses); }
                case 7 -> System.out.println();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option != 7);
    }

    private void submenuExpensesByTime() {

    }

    private void submenuExpensesByCategory() {

    }

    private void submenuManageCategories() {

    }




}
