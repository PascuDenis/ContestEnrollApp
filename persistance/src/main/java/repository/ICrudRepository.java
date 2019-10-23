package repository;

import java.util.List;

public interface ICrudRepository<ID, E> {
    void save(E entity);
    void delete(ID id);
    void update(E entity);
    E findOne(ID id);
    List<E> findAll();
}
