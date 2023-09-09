/* By Agus */
package entities;

import dao.impl.CategoryDAOImpl;

import java.util.Date;
import java.util.Scanner;


public class Expense {
    private int id;
    private float amount;
    private String description;
    private String category;
    private Date date;

    CategoryDAOImpl categoryDAO = new CategoryDAOImpl();
    static Scanner scanner = new Scanner(System.in);


    public Expense(int id, float amount, String description, String category, Date date) {
        this.id = id;
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








}
