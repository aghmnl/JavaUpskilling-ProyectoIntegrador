package dao.impl;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.dto.ExpenseDTO;
import entities.Expense;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static common.ListMethods.printList;
import static config.JDBCConfig.getDBConnection;
import static utils.ScreenMethods.cleanScreen;

public class ExpenseDAOImpl implements ExpenseDAO {
    CategoryDAO categoryDAO = new CategoryDAOImpl();
    private List<ExpenseDTO> allExpensesDTO = getAll();
    Scanner scanner = new Scanner(System.in);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

    private java.util.Date enterDate() {
        boolean dateIsCorrect = false;
        java.util.Date date = new java.util.Date();
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
    
    private TreeSet<String> getMonths () {
        TreeSet<String> months = new TreeSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        for (ExpenseDTO expenses :  allExpensesDTO) {
            months.add(sdf.format(expenses.getDate()));
        }
        return months;
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
        cleanScreen();
        System.out.print("Ingrese el monto del gasto: ");
        float amount = scanner.nextFloat();
        System.out.print("Ingrese la descripción del gasto: ");
        String description = scanner.next();
        String category = categoryDAO.selectCategory("Seleccione la categoría entre las siguientes opciones: ");
        java.util.Date date = enterDate();
        System.out.println("El gasto ingresado es el siguiente: ");
        ExpenseDTO expenseDTO = new ExpenseDTO(amount, description, category, date);
        System.out.println(expenseDTO);
        add(expenseDTO);
        System.out.println();
    }

    @Override
    public void editExpense() {

    }

    @Override
    public void deleteExpense() {

    }

    @Override
    public void showExpenseByCategory() {
        String categorySelected = categoryDAO.selectCategory("Seleccione la categoría para filtrar los gastos: ");
        System.out.println("Los gastos de la categoría " + categorySelected + " son los siguientes:");
        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream().
                filter(e -> Objects.equals(e.getCategory(), categorySelected)).
                toList();
        printList(filteredExpenses);
    }

    @Override
    public void findExpenseByMonth() {
        System.out.println("Elija el mes ");
        printList(getMonths());
    }

    @Override
    public void findExpenseByYear() {

    }

    @Override
    public void showExpenseByDates() {

    }


    public void  findExpenseByDescription() {
        System.out.print("Por favor ingresar el texto a buscar en la descripción: ");
        String textToBeFound = scanner.next();
        int i = 0;
        boolean textFound = false;
        for(ExpenseDTO expense : allExpensesDTO) {
            if(expense.getDescription().contains(textToBeFound)) { System.out.println((i + 1) + ". " + expense); textFound = true;}
            i++;
        }
        if(!textFound) System.out.println("No se encontró ningún gasto con esa descripción.");

    }
    public void findExpenseByAmount() {
        System.out.print("Por favor ingresar el monto a buscar: ");
        int amountToBeFound = scanner.nextInt();
        int i = 0;
        boolean amountFound = false;
        for(ExpenseDTO expense : allExpensesDTO) {
            if(expense.getAmount() == amountToBeFound) { System.out.println((i + 1) + ". " + expense); amountFound = true;}
            i++;
        }
        if(!amountFound) System.out.println("No se encontró ningún gasto con ese monto.");
    }
    public void findExpenseByCategory(){
        String categoryToBeFound = categoryDAO.selectCategory("Seleccione la categoría entre las siguientes opciones: ");
        int i = 0;
        boolean categoryFound = false;
        for(ExpenseDTO expense : allExpensesDTO) {
            if(Objects.equals(expense.getCategory(), categoryToBeFound)) { System.out.println((i + 1) + ". " + expense); categoryFound = true; }
            i++;
        }
        if(!categoryFound) System.out.println("No se encontró ningún gasto en la categoría seleccionada.");

    }
    public void findExpenseByDate(){}
    public void  findExpenseByID(){
        int id;
        boolean IDIsCorrect;
        do {
            System.out.print("Ingrese el ID del gasto a buscar: ");
            id = scanner.nextInt();
            IDIsCorrect = (id > 0) & (id <= allExpensesDTO.size());
            if (!IDIsCorrect) {
                System.out.println("El ID ingresado es incorrecto.");
            }
        } while (!IDIsCorrect);

        System.out.println(allExpensesDTO.get(id-1));
    }
}
