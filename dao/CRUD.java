/* By Agus */
package dao;

import java.util.List;

public interface CRUD<T> {
        int getId(T t);
        List<T> getAll();
        void showAll();
        String add(T t);
        T get(int id);
        void update(T t, int id);
        void delete(int id);
}
