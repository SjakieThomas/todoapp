package be.ucll.examen.apirest;


import be.ucll.examen.entity.Todo;
import be.ucll.examen.services.TodoService;
import be.ucll.examen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/{id}/todo")
public class TodoRestController {

    /* #TODO TODO POSTMAN:
     *   [V] GET
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [V] GET MULTIPLE
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [v] POST
     *     [v] EX1
     *     [v] EX2
     *     [v] EX3
     *   [V] PUT
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [V] DELETE
     *     [V] EX1
     * */


    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    // Endpoint to find TODOs by user ID
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public Optional<ResponseEntity<List<Todo>>> findTodosByUserId(@PathVariable("id") Long id) {
//        Optional<List<Todo>> todos = Optional.of(todoService.findTodosByUserId(id));
//        return Optional.of(todos.filter(list -> !list.isEmpty())
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build()));
//    }



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // @PATH "http://localhost:3718/api/user/:id/todo"
    public ResponseEntity<List<Todo>> findTodosByUserId(@PathVariable("id") Long id) {
        List<Todo> todos = todoService.findTodosByUserId(id);
        if (todos.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.ok(todos);
    }
    // @PATH ( http://localhost:3718/api/user/:id/todo/:todoId)
    @GetMapping(value = "/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> findTodoById(@PathVariable("id") Long userId, @PathVariable("todoId") Long todoId) {
        Optional<Todo> todo = todoService.get(todoId);
        if (todo.isPresent() && todo.get().getUser().getId().equals(userId)) {
            return ResponseEntity.ok(todo.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // @PATH http://localhost:3718/api/user/:id/todo
    public ResponseEntity<Todo> createTodo(@PathVariable("id") Long userId, @RequestBody Todo todo) {
        //Optional<User> getuser= userService.get(userId); // Ensure the TODO item is linked to the user
        Todo createdTodo = todoService.save(todo, userId);
        return ResponseEntity.status(201).body(createdTodo);
    }

    @PutMapping(value = "/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    // @PATH http://localhost:3718/api/user/:id/todo/:todoId
    public ResponseEntity<Todo> updateTodo(@PathVariable("id") Long userId, @PathVariable("todoId") Long todoId, @RequestBody Todo todo) {
        Optional<Todo> existingTodo = todoService.get(todoId);
        if (existingTodo.isPresent() && existingTodo.get().getUser().getId().equals(userId)) {
            todo.setId(todoId);
            Todo updatedTodo = todoService.update(todo);
            return ResponseEntity.ok(updatedTodo);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Endpoint to delete a TODO item by ID
    @DeleteMapping(value = "/{todoId}")
    // @PATH ( http://localhost:3718/api/user/:id/todo/:todoId)
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long userId, @PathVariable("todoId") Long todoId) {
        Optional<Todo> existingTodo = todoService.get(todoId);
        if (existingTodo.isPresent() && existingTodo.get().getUser().getId().equals(userId)) {
            todoService.delete(todoId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }
}