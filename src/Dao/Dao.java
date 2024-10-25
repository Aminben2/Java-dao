package Dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T findById(Long id);
    List<T> findAll();
    void update(Long id, T object);
    void delete(Long id);
    void create(T object);

    Optional<T> getById(Long id);
    List<T> getAll();
    Optional<T> update(T obj);
    T createe(T obj);
    int deleteByid(Long id);
}
