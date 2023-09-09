package dao;

import dao.dto.ExpenseDTO;

public interface ExpenseDAO extends CRUD<ExpenseDTO>{
    String selectExpense(String message);
    void addExpense();
    void editExpense();
    void  deleteExpense();
    void showExpenseByCategory();
    void findExpenseByMonth();
    void findExpenseByYear();
    void showExpenseByDates();
    void findExpenseByID();
    void findExpenseByDescription();
    void findExpenseByAmount();
    void findExpenseByDate();
}
