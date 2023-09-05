package dao.impl;

import dao.CategoryDAO;
import dao.dto.CategoryDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static config.JDBCConfig.getDBConnection;
import static utils.ScreenMethods.cleanScreen;

public class CategoryDAOImpl implements CategoryDAO {

    private final List<CategoryDTO> allCategories = getAll();

    // Para transformar un ResultSet en una lista de EmployeeDTO
    private List<CategoryDTO> resultSetToCategoryList(ResultSet allCategories) throws SQLException {
        List<CategoryDTO> newListCategories = new ArrayList<>();
        while(allCategories.next()) {
            String nombre = allCategories.getString("nombre");
            newListCategories.add(new CategoryDTO(nombre));
        }
        return newListCategories;
    }

    private boolean isCategoryInList(List<CategoryDTO> allCategories, String category) {
        boolean isCategoryInList = false;
        for(CategoryDTO categoryDTO : allCategories) {
            if(Objects.equals(categoryDTO.getCategoryName(), category)) {
                isCategoryInList = true;
                break;
            }
        }
        return isCategoryInList;
    }

    @Override
    public List<CategoryDTO> getAll() {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Sentencia SQL para leer todos los empleados
            String getAllCategoriesSQL = "SELECT * FROM categorias;";

            // Ejecuta la consulta
            ResultSet allCategories = statement.executeQuery(getAllCategoriesSQL);

            // Devuelve el resultado
            return resultSetToCategoryList(allCategories);

        } catch (SQLException e) {
            System.out.println("No se pudieron encontrar todos los empleados");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAll() {
        int i = 1;
        for (CategoryDTO category : allCategories) {
            System.out.println(i + ". " + category.getCategoryName());
            i++;
        }
    }

    @Override
    public String add(CategoryDTO categoryDTO) {
        if(!isCategoryInList(getAll(), categoryDTO.getCategoryName())) {
            try {
                // Establecer la conexión
                Connection connection = getDBConnection();

                // Sentencia SQL para agregar un empleado
                String addEmployeeSQL = "INSERT INTO categorias (nombre) VALUES (?);";

                // Realizar operaciones en la base de datos
                PreparedStatement statement = connection.prepareStatement(addEmployeeSQL);

                // Establecer los valores en el PreparedStatement
                statement.setString(1, categoryDTO.getCategoryName());

                // Ejecutar la inserción
                statement.executeUpdate();

                return "Categoría agregada con éxito";

            } catch (SQLException e) {
                System.out.println("El registro no pudo ser agregado");
                throw new RuntimeException(e);
            }
        } else {
            return "La categoría ya se encuentra en la lista";
        }
    }

    @Override
    public CategoryDTO get(int id) {
        return null;
    }

    @Override
    public void update(CategoryDTO categoryDTO) {

    }

    @Override
    public void delete(int id) {

    }


    static Scanner scanner = new Scanner(System.in);


    public String enterCategory() {
        boolean categoryIsCorrect = false;
        int categoryNumber;
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        do {
            System.out.println("Seleccione la categoría entre las siguientes opciones: ");
            categoryDAO.showAll();
            categoryNumber = scanner.nextInt();
            if ((categoryNumber < 1) || (categoryNumber > allCategories.size())) {
                cleanScreen();
                System.out.println("La categoría seleccionada es incorrecta.");
            } else {
                categoryIsCorrect = true;
            }
        } while(!categoryIsCorrect);
        return allCategories.get(categoryNumber - 1).getCategoryName();
    }


}
