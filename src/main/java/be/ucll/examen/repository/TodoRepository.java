package be.ucll.examen.repository;


import be.ucll.examen.entity.Todo;
import be.ucll.examen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository
        extends
            JpaRepository<Todo, Long>,
            JpaSpecificationExecutor<Todo> {
    List<Todo> findByUserId(Long userId);
}
