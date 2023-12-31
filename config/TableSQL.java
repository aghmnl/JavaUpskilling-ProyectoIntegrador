/* By Agus */
package config;

import java.sql.*;

import static config.JDBCConfig.getDBConnection;
import static utils.Initialization.initializeCategories;
import static utils.Initialization.initializeExpenses;

public class TableSQL {

    public static boolean tableDoesNotExist(String tableName) {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Get the database metadata
            DatabaseMetaData databaseMetaData = connection.getMetaData();

            // Comprueba la existencia de la tabla. El nombre de la misma debe estar en MAYÚSCULAS para detectarla correctamente en SQL
            ResultSet rs = databaseMetaData.getTables(null, null, tableName, null);

            // Devuelve el resultado de la query
            return !rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTableCategories() {
        try {
            System.out.println("Creando tabla 'categorias' e inicializándola con datos de ejemplo");
            System.out.println();
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Creando una tabla Gastos.
            String createTableCategoriesSQL = "CREATE TABLE IF NOT EXISTS categorias (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nombre VARCHAR(50) NOT NULL);";
            statement.executeUpdate(createTableCategoriesSQL);

            // Cerrar la conexión
            statement.close();
            connection.close();


            initializeCategories(); // Sólo a los fines de inicializar la tabla con datos la primera vez que se crea la tabla.

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTableExpenses() {
        try {
            System.out.println("Creando tabla 'gastos' e inicializándola con datos de ejemplo");
            System.out.println();

            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Creando una tabla Gastos
            String createTableExpensesSQL = "CREATE TABLE IF NOT EXISTS gastos (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "monto FLOAT NOT NULL, descripción VARCHAR(255), categoría VARCHAR(50), fecha VARCHAR(10));";

            statement.executeUpdate(createTableExpensesSQL);

            // Cerrar la conexión
            statement.close();
            connection.close();

            initializeExpenses(); // Sólo a los fines de inicializar la tabla con datos.

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
