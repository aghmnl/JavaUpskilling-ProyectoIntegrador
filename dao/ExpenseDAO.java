/* By Agus */
package dao;

import dao.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseDAO extends CRUD<ExpenseDTO>{
    ExpenseDTO selectExpense();
    void addExpense();
    void editExpense();
    void  deleteExpense();
    List<ExpenseDTO> findExpensesByCategory();
    List<ExpenseDTO> findExpensesByDate();
    List<ExpenseDTO> findExpensesByDescription();
    List<ExpenseDTO> findExpensesByAmount();
    ExpenseDTO selectExpenseByCategory();
    ExpenseDTO selectExpenseByDate();
    ExpenseDTO selectExpenseByDescription();
    ExpenseDTO selectExpenseByAmount();
    void showExpensesByPeriod(String period);
    void showExpensesByDates();
}
