package dao.impl;

import dao.CategoryDAO;
import dao.dto.CategoryDTO;
import entities.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static common.ListMethods.printList;
import static config.JDBCConfig.getDBConnection;
import static utils.ScreenMethods.cleanScreen;

public class CategoryDAOImpl implements CategoryDAO {

    private List<CategoryDTO> allCategoriesDTO = getAll();
    Scanner scanner = new Scanner(System.in);

    //Convierte un resultSet con un único registro de categoría en Category
    private Category resultSetToCategory(ResultSet category) {
        int id;
        String categoryName;
        try {
            id = category.getInt("id");
            categoryName = category.getString("nombre");
        } catch (SQLException e) {
            System.out.println("No fue posible encontrar datos de la categoría");
            throw new RuntimeException(e);
        }
        return new Category(id, categoryName);
    }

    // Convierte Category a CategoryDTO. Notar que CategoryDTO no tiene id.
    private CategoryDTO categoryToCategoryDTO (Category category) {
        return new CategoryDTO(category.getCategoryName());
    }

    // Transforma un ResultSet con varios registros en una lista de EmployeeDTO
    private List<CategoryDTO> resultSetToCategoryDTOList(ResultSet allCategories) throws SQLException {
        List<CategoryDTO> newListCategories = new ArrayList<>();
        while(allCategories.next()) {
            newListCategories.add(categoryToCategoryDTO(resultSetToCategory(allCategories)));
        }
        return newListCategories;
    }

    private int categoryInListIndex (List<CategoryDTO> allCategories, String category) {
        int index = -1;
        for (int i = 0; i < allCategories.size(); i++) {
            if(Objects.equals(allCategories.get(i).getCategoryName(), category)) {
                index = i;
                i = allCategories.size();
            } else {
                i++;
            }
        }
        return index;
    }

    private boolean isCategoryInList(List<CategoryDTO> allCategories, String category) {
        return !(categoryInListIndex(allCategories, category) == -1);
    }


    // Dado el nombre de una categoría, lo busca en la lista y devuelve su índice
    private int getCategoryId(String categoryName) {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Sentencia SQL para encontrar una categoría según su nombre
            String getCategorySQL = "SELECT id, nombre FROM categorias WHERE nombre = ?;";

            // Realizar operaciones en la base de datos
            PreparedStatement statement = connection.prepareStatement(getCategorySQL);

            // Establecer los valores en el PreparedStatement
            statement.setString(1, categoryName);

            // Ejecuta la consulta
            ResultSet categoryFound = statement.executeQuery();

            // ATENCIÓN CON ESTE PASO!! CLAVE!! Hay que mover el cursor al primer registro.
            categoryFound.next();

            return (categoryFound.getInt("id"));

        } catch (SQLException e) {
            System.out.println("No se pudieron encontrar todas las categorías");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CategoryDTO> getAll() {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Sentencia SQL para leer todas las categorías
            String getAllCategoriesSQL = "SELECT * FROM categorias;";

            // Ejecuta la consulta
            ResultSet allCategories = statement.executeQuery(getAllCategoriesSQL);

            // Devuelve el resultado
            return resultSetToCategoryDTOList(allCategories);

        } catch (SQLException e) {
            System.out.println("No se pudieron encontrar todas las categorías");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showAll() {
        printList(allCategoriesDTO);
    }

    @Override
    public String add(CategoryDTO categoryDTO) {
        if(!isCategoryInList(getAll(), categoryDTO.getCategoryName())) {
            try {
                // Establecer la conexión
                Connection connection = getDBConnection();

                // Sentencia SQL para agregar un empleado
                String addCategorySQL = "INSERT INTO categorias (nombre) VALUES (?);";

                // Realizar operaciones en la base de datos
                PreparedStatement statement = connection.prepareStatement(addCategorySQL);

                // Establecer los valores en el PreparedStatement
                statement.setString(1, categoryDTO.getCategoryName());

                // Ejecutar la inserción
                statement.executeUpdate();

                // Actualizo la lista de categorías
                allCategoriesDTO = getAll();

                return "Categoría agregada con éxito";

            } catch (SQLException e) {
                System.out.println("La categoría no pudo ser agregada");
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
    public void update(CategoryDTO categoryDTO, int id) {

        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Sentencia SQL para actualizar un empleado según su id
            String updateCategorySQL = "UPDATE categorias SET nombre = ? WHERE id = ?";

            // Realizar operaciones en la base de datos
            PreparedStatement statement = connection.prepareStatement(updateCategorySQL);

            // Establecer los valores en el PreparedStatement
            statement.setString(1, categoryDTO.getCategoryName());
            statement.setInt(2, id);

            // Ejecutar la actualización
            int numberOfRows = statement.executeUpdate();

            // Verificar si la actualización fue exitosa
            if (numberOfRows > 0) {
                // Actualizo la lista de categorías
                allCategoriesDTO = getAll();
                System.out.println("El registro se actualizó exitosamente.");
            } else {
                System.out.println("No se encontró el registro especificado.");
            }

        } catch (SQLException e) {
            System.out.println("El registro no pudo ser actualizado");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {

        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Sentencia SQL para actualizar una categoría según su id
            String deleteCategorySQL = "DELETE FROM categorias WHERE id = ?";

            // Realizar operaciones en la base de datos
            PreparedStatement statement = connection.prepareStatement(deleteCategorySQL);

            // Establecer los valores en el PreparedStatement
            statement.setInt(1, id);

            // Ejecutar la eliminación
            int numberOfRows = statement.executeUpdate();

            // Verificar si la eliminación fue exitosa
            if (numberOfRows > 0) {
                // Actualizo la lista de categorías
                allCategoriesDTO = getAll();
                System.out.println("La categoría se eliminó exitosamente.");
            } else {
                System.out.println("No se pudo eliminar la categoría.");
            }

        } catch (SQLException e) {
            System.out.println("La categoría no pudo ser eliminada");
            throw new RuntimeException(e);
        }
    }

    public String selectCategory(String message) {
        boolean categoryIsCorrect = false;
        int categoryNumber;
        do {
            System.out.println(message);
            showAll();
            categoryNumber = scanner.nextInt();
            if ((categoryNumber < 1) || (categoryNumber > allCategoriesDTO.size())) {
                cleanScreen();
                System.out.println("La categoría seleccionada es incorrecta.");
            } else {
                categoryIsCorrect = true;
            }
        } while(!categoryIsCorrect);
        return allCategoriesDTO.get(categoryNumber - 1).getCategoryName();
    }

    public void addCategory() {
        System.out.print("Ingrese la nueva categoría: ");
        String newCategory = scanner.next();
        System.out.println(add(new CategoryDTO(newCategory)));
        System.out.println();
    }

    public void editCategory() {
        String categorySelected = selectCategory("Seleccione de la lista la categoría a modificar: ");
        System.out.println("Categoría elegida: " + categorySelected);
        System.out.print("Ingrese el nuevo texto: ");
        String newCategory = scanner.next();
        int newId = getCategoryId(categorySelected);
        update(new CategoryDTO(newCategory), newId);
    }

    public void deleteCategory() {
        String categorySelected = selectCategory("Seleccione de la lista la categoría a eliminar: ");
        System.out.println("Está seguro que desea eliminar la categoría " + categorySelected + "? (S/N)");
        String opcionElegida = scanner.next().toUpperCase();
        while (!Objects.equals(opcionElegida, "S") && !Objects.equals(opcionElegida, "N")) {
            System.out.println("Respuesta incorrecta, por favor elegir entre S (Sí) y N (No)");
            opcionElegida = scanner.next().toUpperCase();
        };
        if(opcionElegida.equals("S")) {
            delete(getCategoryId(categorySelected));
        } else {
            System.out.println("La categoría no fue eliminada");
        }
    };

}
