package be.ucll.examen.repository;


import be.ucll.examen.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository
        extends
            JpaRepository<Todo, Long>,
            JpaSpecificationExecutor<Todo> {
    List<Todo> findByUserId(Long userId);

    //List<Todo> findByTodoFor(TodoFor todoFor);
    //List<Todo> findByStatus(TodoStatus todoStatus);
    //List<Todo> findByTodoForAndStatus(TodoFor todoFor, TodoStatus todoStatus);
}
