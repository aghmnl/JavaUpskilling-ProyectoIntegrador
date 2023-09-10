/* By Agus */
package dao.dto;

import java.util.Date;

import static common.DateMethods.dateFormat;

public class ExpenseDTO {
    private float amount;
    private String description;
    private String category;
    private Date date;

    public ExpenseDTO(float amount, String description, String category, Date date) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.date = date;
    }

    public ExpenseDTO() {
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

    @Override
    public String toString() {
        return "$" + this.amount + " " + this.description + " [" + this.category + "] " + dateFormat.format(this.date);
    }
}
