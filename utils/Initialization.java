/* By Agus */
package utils;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.dto.CategoryDTO;
import dao.dto.ExpenseDTO;
import dao.impl.CategoryDAOImpl;
import dao.impl.ExpenseDAOImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Initialization {


    public static void initializeExpenses() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ExpenseDAO expenses = new ExpenseDAOImpl();
        Date date1 = new Date();
        Date date2 = new Date();
        Date date3 = new Date();
        Date date4 = new Date();
        try {
            date1 = dateFormat.parse("10-09-2023");
            date2 = dateFormat.parse("23-07-2023");
            date3 = dateFormat.parse("15-02-2023");
            date4 = dateFormat.parse("06-05-2023");
        } catch (Exception e) {
            System.out.println("Formato de fecha inválido");
        }
        expenses.add(new ExpenseDTO(10, "frutas", "Supermercado", date1));
        expenses.add(new ExpenseDTO( 54, "cena", "Restaurant", date2));
        expenses.add(new ExpenseDTO(78, "limpieza", "Auto", date3));
        expenses.add(new ExpenseDTO(25, "luz", "Casa", date4));
    }

    public static void initializeCategories() {
        CategoryDAO categories = new CategoryDAOImpl();
        categories.add(new CategoryDTO("Auto"));
        categories.add(new CategoryDTO("Casa"));
        categories.add(new CategoryDTO("Supermercado"));
        categories.add(new CategoryDTO("Restaurant"));
        categories.add(new CategoryDTO("Otra"));
    }
}
