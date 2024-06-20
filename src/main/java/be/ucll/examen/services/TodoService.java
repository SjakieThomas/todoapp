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
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public Optional<Todo> get(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<Todo> findTodosByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Page<Todo> list(Pageable pageable) {
        User user = getCurrentUser();
        Specification<Todo> spec = Specification.where((root, query, cb) -> cb.equal(root.get("user"), user));
        spec = spec.and((root, query, cb) -> cb.notEqual(root.get("status"), "DONE"));
        Sort sort = Sort.by(Sort.Direction.ASC, "dueDate");
        pageable = PageRequest.of(0, 10, sort);
        return repository.findAll(spec, pageable);
    }

    public Page<Todo> list(Pageable pageable, Specification<Todo> filter) {
        User user = getCurrentUser();
        filter = filter.and((root, query, cb) -> cb.equal(root.get("user"), user));
        return repository.findAll(filter, pageable);
    }

    public int count() {
        User user = getCurrentUser();
        return (int) repository.count((root, query, cb) -> cb.equal(root.get("user"), user));
    }

    public Page<Todo> findByFilter(TodoFor todoFor, TodoStatus status, LocalDate fromDate, LocalDate tillDate, PageRequest pageRequest) {
        User user = getCurrentUser();
        Specification<Todo> spec = Specification.where((root, query, cb) -> cb.equal(root.get("user"), user));
        if (todoFor != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("todoFor"), todoFor));
        }
        if (status != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), status));
        }
        if (fromDate != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("creationDate"), fromDate));
        }
        if (tillDate != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("dueDate"), tillDate));
        }
        return repository.findAll(spec, pageRequest);
    }

    public Todo save(Todo todo) {
        todo.setUser(getCurrentUser());
        repository.save(todo);
        return todo;
    }


    public Todo save(Todo todo,Long userId) {
        userRepository.findById(userId).ifPresent(todo::setUser);
        repository.save(todo);
        return todo;
    }

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

    private User getCurrentUser() {
        return authenticatedUser.get().orElseThrow(() -> new RuntimeException("No authenticated user found"));
    }

    public void delete(Todo todo) {
        repository.delete(todo);
    }
}
