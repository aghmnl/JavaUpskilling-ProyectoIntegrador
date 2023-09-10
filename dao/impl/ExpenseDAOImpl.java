/* By Agus */
package dao.impl;

import dao.CategoryDAO;
import dao.ExpenseDAO;
import dao.dto.ExpenseDTO;
import entities.Expense;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static common.DateMethods.dateFormat;
import static common.DateMethods.enterDate;
import static common.ListMethods.printList;
import static common.ListMethods.selectTFromList;
import static config.JDBCConfig.getDBConnection;
import static utils.ScreenMethods.cleanScreen;

public class ExpenseDAOImpl implements ExpenseDAO {
    CategoryDAO categoryDAO = new CategoryDAOImpl();
    private List<ExpenseDTO> allExpensesDTO = getAll();
    Scanner scanner = new Scanner(System.in);

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

    // Devuelve todos los meses en los que hay gastos
    private List<String> getMonths (SimpleDateFormat sdf) {
        TreeSet<String> months = new TreeSet<>();
        for (ExpenseDTO expenses :  allExpensesDTO) {
            months.add(sdf.format(expenses.getDate()));
        }
        return new ArrayList<>(months); // Acá convierto el TreeSet<String> en ArrayList<String>
    }
    public int getId(ExpenseDTO expenseDTO) {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Sentencia SQL para encontrar una gasto según todos sus campos. Se limita a devolver el primer gasto
            // que cumpla con el criterio ya que no hay restricción de poner gastos con todos los campos iguales.
            String getExpenseSQL = "SELECT id FROM gastos WHERE monto = ? AND descripción = ? AND categoría = ? AND fecha = ? ORDER BY id LIMIT 1;";

            // Realizar operaciones en la base de datos
            PreparedStatement statement = connection.prepareStatement(getExpenseSQL);

            // Establecer los valores en el PreparedStatement
            statement.setFloat(1, expenseDTO.getAmount());
            statement.setString(2, expenseDTO.getDescription());
            statement.setString(3, expenseDTO.getCategory());
            statement.setDate(4, new java.sql.Date(expenseDTO.getDate().getTime()));

            // Ejecuta la consulta
            ResultSet expenseFound = statement.executeQuery();

            // ATENCIÓN CON ESTE PASO!! CLAVE!! Hay que mover el cursor al primer registro.
            expenseFound.next();

            return (expenseFound.getInt("id"));

        } catch (SQLException e) {
            System.out.println("No se pudieron encontrar los gastos");
            throw new RuntimeException(e);
        }
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
            System.out.println("No se pudieron encontrar los gastos");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAll() { printList(allExpensesDTO); }

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
    public ExpenseDTO selectExpense() {
        return selectTFromList(allExpensesDTO, "Seleccione el gasto deseado:");
    }

    @Override
    public void addExpense() {
        cleanScreen();
        System.out.print("Ingrese el monto del gasto: ");
        float amount = scanner.nextFloat();
        System.out.print("Ingrese la descripción del gasto: ");
        String description = scanner.next();
        String category = categoryDAO.selectCategory("Seleccione la categoría entre las siguientes opciones: ");
        java.util.Date date = enterDate("Ingrese la fecha en el formato dd-mm-aaaa: ");
        System.out.println("El gasto ingresado es el siguiente: ");
        ExpenseDTO expenseDTO = new ExpenseDTO(amount, description, category, date);
        System.out.println(expenseDTO);
        add(expenseDTO);
        System.out.println();
    }

    @Override
    public void editExpense() {
        System.out.println("Éste es el id del gasto solicitado: " + getId(submenuManageExpenses()));
    }

    @Override
    public void deleteExpense() {

    }

    private ExpenseDTO submenuManageExpenses() {
        int option = 1;
        ExpenseDTO expenseDTO = new ExpenseDTO();

        do {
            cleanScreen();
            System.out.println("Ingrese el método de búsqueda del gasto a gestionar: ");
            System.out.println("   1. Por descripción.");
            System.out.println("   2. Por monto.");
            System.out.println("   3. Por categoría.");
            System.out.println("   4. Por fecha.");
            System.out.println("   5. Listar todos los gastos.");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> expenseDTO = selectExpenseByDescription();
                case 2 -> expenseDTO = selectExpenseByAmount();
                case 3 -> expenseDTO = selectExpenseByCategory();
                case 4 -> expenseDTO = selectExpenseByDate();
                case 5 -> expenseDTO = selectExpense();
                default -> System.out.println("La opción ingresada no es válida");
            }
        } while (option < 1 || option > 5);

        return expenseDTO;
    }

    @Override
    public List<ExpenseDTO> findExpensesByCategory() {
        String categorySelected = categoryDAO.selectCategory("Seleccione la categoría para filtrar los gastos: ");

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream().
                filter(e -> Objects.equals(e.getCategory(), categorySelected)).
                toList();

        System.out.println("Los gastos de la categoría " + categorySelected + " son:");
        printList(filteredExpenses);

        return filteredExpenses;
    }

    @Override
    public List<ExpenseDTO> findExpensesByDate() {
        java.util.Date selectedDate = enterDate("Ingrese la fecha en el formato dd-mm-aaaa: ");

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream()
                .filter(e -> dateFormat.format(e.getDate()).equals(dateFormat.format(selectedDate)) ) // Acá agrego un día al endDate para que el filtrado sea inclusive
                .toList();

        if(filteredExpenses.isEmpty()) {
            System.out.println("No se encontró ningún gasto con esa descripción.");
        } else {
            System.out.println("Los gastos en la fecha " + dateFormat.format(selectedDate) + " son: ");
            printList(filteredExpenses);
        }

        return filteredExpenses;
    }

    public List<ExpenseDTO> findExpensesByDescription() {
        System.out.print("Por favor ingresar el texto a buscar en la descripción: ");
        String textToBeFound = scanner.next();

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream()
                .filter(e -> e.getDescription().contains(textToBeFound))
                .toList();

        if(filteredExpenses.isEmpty()) {
            System.out.println("No se encontró ningún gasto con esa descripción.");
        } else {
            System.out.println("Los gastos con el texto " + textToBeFound + " son: ");
            printList(filteredExpenses);
        }

        return filteredExpenses;
    }

    public List<ExpenseDTO> findExpensesByAmount() {
        System.out.print("Por favor ingresar el monto a buscar: ");
        float amountToBeFound = scanner.nextFloat();

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream()
                .filter(e -> e.getAmount() == amountToBeFound)
                .toList();

        if(filteredExpenses.isEmpty()) {
            System.out.println("No se encontró ningún gasto con ese monto.");
        } else {
            System.out.println("Los gastos con el monto " + amountToBeFound + " son: ");
            printList(filteredExpenses);
        }

        return filteredExpenses;
    }

    @Override
    public ExpenseDTO selectExpenseByCategory() {
        return selectTFromList(findExpensesByCategory(), "Elija el gasto de la lista filtrada por categoría: ");
    }

    @Override
    public ExpenseDTO selectExpenseByDate() {
        return selectTFromList(findExpensesByDate(), "Elija el gasto de la lista filtrada por fecha: ");
    }

    @Override
    public ExpenseDTO selectExpenseByDescription() {
        return selectTFromList(findExpensesByDescription(), "Elija el gasto de la lista filtrada por descripción: ");
    }

    @Override
    public ExpenseDTO selectExpenseByAmount() {
        return selectTFromList(findExpensesByAmount(), "Elija el gasto de la lista filtrada por monto: ");
    }

    @Override
    public void showExpensesByPeriod(String period) {
        SimpleDateFormat sdf = new SimpleDateFormat(period);
        List<String> periodList = getMonths(sdf);

        String periodSelected = selectTFromList(periodList,  "Seleccione la opción deseada para filtrar los gastos:");

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream().
                filter(e -> sdf.format(e.getDate()).equals(periodSelected)).
                toList();

        System.out.println("Los gastos en el período " + periodSelected + " son: ");
        printList(filteredExpenses);
    }

    @Override
    public void showExpensesByDates() {
        java.util.Date startDate = enterDate("Ingrese la primera fecha en el formato dd-mm-aaaa: ");
        java.util.Date endDate = enterDate("Ingrese la última fecha en el formato dd-mm-aaaa: ");

        List<ExpenseDTO> filteredExpenses = allExpensesDTO.stream().
                filter(e -> e.getDate().after(startDate) && e.getDate().before(new java.util.Date(endDate.getTime() + 24 * 60 * 60 * 1000))) // Acá agrego un día al endDate para que el filtrado sea inclusive
                .toList();

        if(filteredExpenses.isEmpty()) {
            System.out.println("No se encontró ningún gasto entre esas fecha.");
        } else {
            System.out.println("Los gastos entre el " + dateFormat.format(startDate) + " y el " + dateFormat.format(endDate) + " (inclusive) son: ");
            printList(filteredExpenses);
        }
    }
}
