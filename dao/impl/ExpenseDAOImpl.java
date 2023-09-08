package dao.impl;

import dao.ExpenseDAO;
import dao.dto.ExpenseDTO;
import entities.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static config.JDBCConfig.getDBConnection;
import static utils.ScreenMethods.cleanScreen;

public class ExpenseDAOImpl implements ExpenseDAO {
    
    private List<ExpenseDTO> allExpensesDTO = getAll();

    //Convierte un resultSet con un único registro de gasto en Expense
    private Expense resultSetToExpense(ResultSet expense) {
        int id;
        float amount;
        String description;
        String category;
        java.util.Date date;

        try {
            id = expense.getInt("id");
            amount = expense.getFloat("monto");
            description = expense.getString("descripción");
            category = expense.getString("categoría");
            date = expense.getDate("fecha");
        } catch (SQLException e) {
            System.out.println("No fue posible encontrar datos del gasto");
            throw new RuntimeException(e);
        }
        return new Expense(id, amount, description, category, date);
    }

    // Convierte Expense en ExpenseDTO. Notar que ExpenseDTO no tiene id.
    private ExpenseDTO expenseToExpenseDTO (Expense expense) {
        return new ExpenseDTO(expense.getAmount(), expense.getDescription(), expense.getCategory(), expense.getDate());
    }


    // Transforma un ResultSet con varios registros en una lista de EmployeeDTO
    private List<ExpenseDTO> resultSetToExpenseDTOList(ResultSet allExpenses) throws SQLException {
        List<ExpenseDTO> newListExpenses = new ArrayList<>();
        while(allExpenses.next()) {
            newListExpenses.add(expenseToExpenseDTO(resultSetToExpense(allExpenses)));
        }
        return newListExpenses;
    }

    @Override
    public List<ExpenseDTO> getAll() {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Sentencia SQL para leer todos los gastos
            String getAllExpensesSQL = "SELECT * FROM gastos;";

            // Ejecuta la consulta
            ResultSet allExpenses = statement.executeQuery(getAllExpensesSQL);

            // Devuelve el resultado
            return resultSetToExpenseDTOList(allExpenses);

        } catch (SQLException e) {
            System.out.println("No se pudieron encontrar todos los gastos");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAll() {
        int i = 0;
        cleanScreen();
        for (ExpenseDTO e : allExpensesDTO) {
            System.out.println(++i + ". " + e);
        }
    }

    @Override
    public String add(ExpenseDTO expenseDTO) {

        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Sentencia SQL para agregar un gasto
            String addExpenseSQL = "INSERT INTO gastos (monto, descripción, categoría, fecha) VALUES (?, ?, ?, ?);";

            // Realizar operaciones en la base de datos
            PreparedStatement statement = connection.prepareStatement(addExpenseSQL);

            // Establecer los valores en el PreparedStatement
            statement.setFloat(1, expenseDTO.getAmount());
            statement.setString(2, expenseDTO.getDescription());
            statement.setString(3, expenseDTO.getCategory());
            statement.setDate(4, new java.sql.Date(expenseDTO.getDate().getTime()));

            // Ejecutar la inserción
            statement.executeUpdate();

            // Actualizo la lista de gastos
            allExpensesDTO = getAll();

            return "Gasto agregado con éxito";

        } catch (SQLException e) {
            System.out.println("El gasto no pudo ser agregado");
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExpenseDTO get(int id) {
        return null;
    }

    @Override
    public void update(ExpenseDTO expenseDTO, int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public String selectExpense(String message) {
        return null;
    }

    @Override
    public void addExpense() {

    }

    @Override
    public void editExpense() {

    }

    @Override
    public void deleteExpense() {

    }

    @Override
    public void showExpenseByCategory() {

    }
}
