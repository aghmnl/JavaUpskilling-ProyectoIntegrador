/* By Agus */

package datos;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;


import static datos.Category.enterCategory;
import static menu.Menu.*;
import static utils.ScreenMethods.cleanScreen;

public class Expense {
    private float amount;
    private String description;
    private String category;
    private Date date;

    static Scanner scanner = new Scanner(System.in);


    public Expense(float amount, String description, String category, Date date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public Expense() {
        this.amount = 0;
        this.description = "";
        this.category = "Otra";
        this.date = new Date();
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public static Date enterDate() {
        boolean dateIsCorrect = false;
        Date date = new Date();
        do {
            System.out.print("Ingrese la fecha en el formato dd-mm-aaaa: ");
            String dateString = scanner.next();
            try {
                date = dateFormat.parse(dateString);
                dateIsCorrect = true;
            } catch (Exception e) {
                cleanScreen();
                System.out.println("El formato de la fecha es incorrecto.");
            }
        } while (!dateIsCorrect);
        return date;
    }

    @Override
    public String toString() {
        return "$" + this.amount + " " + this.description + " [" + this.category + "] " + dateFormat.format(this.date);
    }

    public static void  findExpenseByDescription() {
        System.out.print("Por favor ingresar el texto a buscar en la descripción: ");
        String textToBeFound = scanner.next();
        int i = 0;
        boolean textFound = false;
        for(Expense expense : expenses) {
            if(expense.description.contains(textToBeFound)) { System.out.println((i + 1) + ". " + expense); textFound = true;}
            i++;
        }
        if(!textFound) System.out.println("No se encontró ningún gasto con esa descripción.");

    }
    public static void findExpenseByAmount() {
        System.out.print("Por favor ingresar el monto a buscar: ");
        int amountToBeFound = scanner.nextInt();
        int i = 0;
        boolean amountFound = false;
        for(Expense expense : expenses) {
            if(expense.amount == amountToBeFound) { System.out.println((i + 1) + ". " + expense); amountFound = true;}
            i++;
        }
        if(!amountFound) System.out.println("No se encontró ningún gasto con ese monto.");
    }
    public static void findExpenseByCategory(){
        String categoryToBeFound = enterCategory();
        int i = 0;
        boolean categoryFound = false;
        for(Expense expense : expenses) {
            if(Objects.equals(expense.category, categoryToBeFound)) { System.out.println((i + 1) + ". " + expense); categoryFound = true; }
            i++;
        }
        if(!categoryFound) System.out.println("No se encontró ningún gasto en la categoría seleccionada.");

    }
    public static void findExpenseByDate(){}
    public static void  findExpenseByID(){
        int ID;
        boolean IDIsCorrect;
        do {
            System.out.print("Ingrese el ID del gasto a buscar: ");
            ID = scanner.nextInt();
            IDIsCorrect = (ID > 0) & (ID <= expenses.size());
            if (!IDIsCorrect) {
                System.out.println("El ID ingresado es incorrecto.");
            }
        } while (!IDIsCorrect);

        System.out.println(expenses.get(ID-1));
    }
    public static void  listExpenses(List<Expense> expenses){
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

}
