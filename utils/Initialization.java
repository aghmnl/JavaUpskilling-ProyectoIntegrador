package utils;

import datos.Expense;

import java.util.Date;

import static menu.Menu.*;

public class Initialization {


    public static void initializeExpenses() {
        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        Date date4 = null;
        try {
            date1 = dateFormat.parse("10-09-2023");
            date2 = dateFormat.parse("23-07-2023");
            date3 = dateFormat.parse("15-02-2023");
            date4 = dateFormat.parse("06-05-2023");
        } catch (Exception e) {
            System.out.println("Invalid date format");
        }
        Expense expense1 = new Expense(10, "frutas", "Supermercado", date1);
        Expense expense2 = new Expense(54, "cena", "Restaurant", date2);
        Expense expense3 = new Expense(78, "limpieza", "Auto", date3);
        Expense expense4 = new Expense(25, "luz", "Casa", date4);
        expenses.add(expense1);
        expenses.add(expense2);
        expenses.add(expense3);
        expenses.add(expense4);
    }

    public static void initializeCategories() {
        categories.add(0, "Supermercado");
        categories.add(0, "Casa");
        categories.add(0, "Auto");
        categories.add(0,"Restaurant");
    }
}
