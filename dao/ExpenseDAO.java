/* By Agus */
package dao;

import dao.dto.ExpenseDTO;

public interface ExpenseDAO extends CRUD<ExpenseDTO>{
    ExpenseDTO selectExpense(String message);
    void addExpense();
    void editExpense();
    void  deleteExpense();
    void showExpenseByCategory();
    void findExpenseByPeriod(String period);
    void showExpenseByDates();
    void showExpenseByDate();
    void findExpenseByDescription();
    void findExpenseByAmount();
}
