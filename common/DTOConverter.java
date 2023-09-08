package common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DTOConverter {

    public static <T> T resultSetToT(ResultSet resultSet, Class<T> dtoClass) {
        T dto;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
            System.out.println("Éste es el dto " + dto);
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                System.out.println("éste es el columnName " + columnName);
                if(!Objects.equals(columnName, "ID")) {
                Object value = resultSet.getObject(i);
                Field field = dtoClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(dto, value); }
            }
            return dto;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static <T> List<T> resultSetToTList(ResultSet resultSet, Class<T> dtoClass) throws SQLException {
        List<T> newListT = new ArrayList<>();
        while (resultSet.next()) {
            newListT.add(resultSetToT(resultSet, dtoClass));
        }
        return newListT;
    }
}
