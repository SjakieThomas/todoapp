package be.ucll.examen.repository;


import be.ucll.examen.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/** This interface represents a repository for managing {@link Todo} entities.
 * It extends Spring Data JPA's {@link JpaRepository} and {@link JpaSpecificationExecutor} interfaces.
 * @author Thomas Vogelaers
 * @since 1.0.0
 */
@Repository
public interface TodoRepository
        extends
            JpaRepository<Todo, Long>,
            JpaSpecificationExecutor<Todo> {

    /** Finds all {@link Todo} entities by the given user ID.
     * @param userId the ID of the User whose {@link Todo} entities should be retrieved
     * @return a list of {@link Todo} entities associated with the given user ID
     */
    List<Todo> findByUserId(Long userId);

    //List<Todo> findByTodoFor(TodoFor todoFor);
    //List<Todo> findByStatus(TodoStatus todoStatus);
    //List<Todo> findByTodoForAndStatus(TodoFor todoFor, TodoStatus todoStatus);
}
