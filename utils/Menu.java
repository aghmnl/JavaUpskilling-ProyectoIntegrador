/* By Agus */
package utils;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.impl.CategoryDAOImpl;
import dao.impl.ExpenseDAOImpl;

import java.util.Scanner;

import static utils.ScreenMethods.cleanScreen;

public class Menu {
    ExpenseDAO expenseDAO = new ExpenseDAOImpl();
    CategoryDAO categoryDAO = new CategoryDAOImpl();

//    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); // Defining the format of the date to be used along the code

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
            if(!expenseDAO.getAll().isEmpty()) System.out.println("   5. Gestionar gastos.");
            if(!categoryDAO.getAll().isEmpty()) System.out.println("   6. Gestionar categorías.");
            System.out.println("   7. Salir de la aplicación.");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> expenseDAO.addExpense();
                case 2 -> expenseDAO.showAll();
                case 3 -> expenseDAO.showExpenseByCategory();
                case 4 -> submenuTimeFiltering();
                case 5 -> submenuManageExpenses();
                case 6 -> submenuManageCategories();
                case 7 -> {
                    System.out.println();
                    System.out.println("Gracias por utilizar el gestor de gastos!!");
                    System.out.println("Made by Agus.");
                }
                default -> { cleanScreen(); System.out.println("La opción ingresada no es válida");}
            }
        } while (option != 7);

        scanner.close();   // This is to close the scanner. It should only be done at the very end of the code.
    }


    private void submenuManageExpenses() {
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
                case 1 -> expenseDAO.findExpenseByDescription();
                case 2 -> expenseDAO.findExpenseByAmount();
                case 3 -> expenseDAO.showExpenseByCategory();
                case 4 -> expenseDAO.findExpenseByDate();
                case 5 -> expenseDAO.findExpenseByID();
                case 6 -> expenseDAO.showAll();
                case 7 -> System.out.println();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option != 7);
    }


    private void submenuManageCategories() {
        int option;

        do {
            cleanScreen();
            System.out.println("Ingrese la opción deseada: ");
            System.out.println("   1. Ver todas las categorías.");
            System.out.println("   2. Agregar categoría.");
            System.out.println("   3. Modificar categoría.");
            System.out.println("   4. Eliminar categoría.");
            System.out.println("   5. Ir al menú anterior.");
            option = scanner.nextInt();

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
            System.out.println("   4. Ir al menú anterior.");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> expenseDAO.findExpenseByPeriod("MM");
                case 2 -> expenseDAO.findExpenseByPeriod("YYYY");
                case 3 -> expenseDAO.showExpenseByDates();
                case 4 -> System.out.println();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option != 4);
    }

}
