package Dao;

import java.util.List;

public interface Dao<T> {
    T findById(Long id);
    List<T> findAll();
    void update(Long id, T object);
    void delete(Long id);
    void create(T object);
}
