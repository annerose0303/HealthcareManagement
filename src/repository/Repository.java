package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, T> {
    List<T> findAll();
    Optional<T> findById(ID id);
    void save(T entity);        // upsert
    boolean deleteById(ID id);
}
