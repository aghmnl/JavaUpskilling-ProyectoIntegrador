package dao;

import java.util.List;

public interface CRUD<T> {
        List<T> getAll();
        void showAll();
        String add(T t);
        T get(int id);
        void update(T t);
        void delete(int id);
}
