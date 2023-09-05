package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static config.JDBCConfig.getDBConnection;

public class TableSQL {
    public static void createTableCategories() {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Creando una tabla Gastos
            String createTableCategoriesSQL = "CREATE TABLE IF NOT EXISTS categorias (nombre VARCHAR(50) NOT NULL);";
            statement.executeUpdate(createTableCategoriesSQL);

            // Cerrar la conexión
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTableExpenses() {
        try {
            // Establecer la conexión
            Connection connection = getDBConnection();

            // Realizar operaciones en la base de datos
            Statement statement = connection.createStatement();

            // Creando una tabla Gastos
            String createTableExpensesSQL = "CREATE TABLE IF NOT EXISTS gastos (id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "monto FLOAT NOT NULL, descripcion VARCHAR(255), categoria VARCHAR(50), fecha VARCHAR(10));";

            statement.executeUpdate(createTableExpensesSQL);

            // Cerrar la conexión
            statement.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
