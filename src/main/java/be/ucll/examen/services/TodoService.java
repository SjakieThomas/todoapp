package be.ucll.examen.services;

import be.ucll.examen.entity.Todo;
import be.ucll.examen.entity.TodoFor;
import be.ucll.examen.entity.TodoStatus;
import be.ucll.examen.entity.User;
import be.ucll.examen.repository.TodoRepository;
import be.ucll.examen.repository.UserRepository;
import be.ucll.examen.security.AuthenticatedUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/** Service class for managing Todo entities.
 */
@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    /** Retrieves a Todo by its ID.
     * @param id The ID of the Todo to retrieve.
     * @return An Optional containing the Todo if found, otherwise an empty Optional.
     */
    public Optional<Todo> get(Long id) {
        return repository.findById(id);
    }

    /** Deletes a Todo by its ID.
     * @param id The ID of the Todo to delete.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /** Retrieves all Todos for a specific user.
     * @param userId The ID of the user whose Todos to retrieve.
     * @return A list of Todos for the specified user.
     */
    public List<Todo> findTodosByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    /** Retrieves a paginated list of Todos for the authenticated user, excluding completed tasks.
     * @param pageable The Pageable object for pagination and sorting.
     * @return A Page of Todos.
     */
    public Page<Todo> list(Pageable pageable) {
        User user = getCurrentUser();
        Specification<Todo> spec = Specification.where((root, query, cb) -> cb.equal(root.get("user"), user));
        spec = spec.and((root, query, cb) -> cb.notEqual(root.get("status"), "DONE"));
        Sort sort = Sort.by(Sort.Direction.ASC, "dueDate");
        pageable = PageRequest.of(0, 10, sort);
        return repository.findAll(spec, pageable);
    }

    /** Retrieves a paginated list of Todos based on the provided filter and the authenticated user.
     * @param pageable The Pageable object for pagination and sorting.
     * @param filter   The Specification for filtering Todos.
     * @return A Page of Todos.
     */
    public Page<Todo> list(Pageable pageable, Specification<Todo> filter) {
        User user = getCurrentUser();
        filter = filter.and((root, query, cb) -> cb.equal(root.get("user"), user));
        return repository.findAll(filter, pageable);
    }

    /** Counts the number of Todos for the authenticated user.
     * @return The total count of Todos for the authenticated user.
     */
    // TODO
    public int count() {
        User user = getCurrentUser();
        return (int) repository.count((root, query, cb) -> cb.equal(root.get("user"), user));
    }

    /** Retrieves a paginated list of Todos based on the provided filter criteria and the authenticated user.
     * @param todoFor     The TodoFor filter.
     * @param status      The TodoStatus filter.
     * @param fromDate    The minimum creation date filter.
     * @param tillDate    The maximum due date filter.
     * @param pageRequest The Pageable object for pagination and sorting.
     * @return A Page of Todos.
     */
    //TODO
    public Page<Todo> findByFilter(TodoFor todoFor, TodoStatus status, LocalDate fromDate, LocalDate tillDate, PageRequest pageRequest) {
        User user = getCurrentUser();
        Specification<Todo> spec = Specification.where((root, query, cb) -> cb.equal(root.get("user"), user));
        if (todoFor != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("todoFor"), todoFor));
        if (status != null) spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        if (fromDate != null) spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("creationDate"), fromDate));
        if (tillDate != null) spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), tillDate));
        return repository.findAll(spec, pageRequest);
    }

    /** Saves a new Todo for the authenticated user.
     * @param todo The Todo to save.
     */
    public void save(Todo todo) {
        todo.setUser(getCurrentUser());
        repository.save(todo);
    }

    /** Saves a new Todo for a specific user.
     * @param todo    The Todo to save.
     * @param userId  The ID of the user to associate the Todo with.
     * @return The saved Todo.
     */
    public Todo save(Todo todo,Long userId) {
        userRepository.findById(userId).ifPresent(todo::setUser);
        repository.save(todo);
        return todo;
    }

    /** Updates an existing Todo with the provided data.
     * @param updatedTodo The updated Todo data.
     * @return The updated Todo.
     * @throws EntityNotFoundException If the Todo with the provided ID does not exist.
     */
    public Todo update(Todo updatedTodo) {
        Optional<Todo> existingTodoOptional = repository.findById(updatedTodo.getId());

        if (existingTodoOptional.isPresent()) {
            Todo existingTodo = existingTodoOptional.get();
            Optional.ofNullable(updatedTodo.getTitle()).ifPresent(existingTodo::setTitle);
            Optional.ofNullable(updatedTodo.getComment()).ifPresent(existingTodo::setComment);
            Optional.ofNullable(updatedTodo.getCreationDate()).ifPresent(existingTodo::setCreationDate);
            Optional.ofNullable(updatedTodo.getDueDate()).ifPresent(existingTodo::setDueDate);
            Optional.ofNullable(updatedTodo.getStatus()).ifPresent(existingTodo::setStatus);
            Optional.ofNullable(updatedTodo.getTodoFor()).ifPresent(existingTodo::setTodoFor);
            return repository.save(existingTodo);
        } else {
            throw new EntityNotFoundException("Todo not found with id " + updatedTodo.getId());
        }
    }

    /** Retrieves the authenticated user.
     * @return The authenticated user.
     * @throws RuntimeException If no authenticated user is found.
     */
    private User getCurrentUser() {
        return authenticatedUser.get().orElseThrow(() -> new RuntimeException("No authenticated user found"));
    }

    /** Deletes a Todo.
     * @param todo The Todo to delete.
     */
    public void delete(Todo todo) {
        repository.delete(todo);
    }
}
