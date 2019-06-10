package kosiorek.michal.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {

    List<T> findAll();
    Optional<T> findById(Integer id);
    void add(T element);
    void update(T element);
    void delete(Integer id);

}
