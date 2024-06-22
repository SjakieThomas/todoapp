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

/** This class is a REST controller for managing TODO items associated with a specific user.
* It handles HTTP requests related to CRUD operations on TODO items.
* @author Thomas Vogelaers
*/
@RestController
@RequestMapping("/api/user/{id}/todo")
public class TodoRestController {


    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService userService;

    /** Retrieves a list of TODO items associated with a specific user.
    * @param id The ID of the user for which to retrieve TODO items.
    * @return A ResponseEntity containing a list of TODO items or a 204 No Content status if the list is empty.
    * @PATH /api/user/:id/todo
    * @throws IllegalArgumentException if the provided user ID is null.
    * @throws IllegalStateException if the user with the given ID does not exist.
    * @throws Exception if an error occurs while retrieving the TODO items.
    */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Todo>> findTodosByUserId(@PathVariable("id") Long id) {
        List<Todo> todos = todoService.findTodosByUserId(id);
        if (todos.isEmpty()) {
            return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.ok(todos);
    }
    /** Retrieves a specific TODO item by its ID and user ID.
    * @param userId The ID of the user associated with the TODO item.
    * @param todoId The ID of the TODO item to retrieve.
    * @return A ResponseEntity containing the requested TODO item if found and the user ID matches,
    *         or a 404 Not Found status if the TODO item is not found or does not belong to the specified user.
    * @throws IllegalArgumentException if the provided user ID or TODO item ID is null.
    * @throws IllegalStateException if the user with the given ID does not exist.
    * @throws Exception if an error occurs while retrieving the TODO item.
    * @PATH /api/user/:id/todo/:todoId
    */
    @GetMapping(value = "/{todoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> findTodoById(@PathVariable("id") Long userId, @PathVariable("todoId") Long todoId) {
        Optional<Todo> todo = todoService.get(todoId);
        if (todo.isPresent() && todo.get().getUser().getId().equals(userId)) {
            return ResponseEntity.ok(todo.get());
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    /** Endpoint to create a new TODO item associated with a specific user.
    * @param userId The ID of the user for whom the TODO item will be created.
    * @param todo The TODO item to be created. The user ID in the provided TODO item should match the provided user ID.
    * @return A ResponseEntity containing the created TODO item with a 201 Created status.
    * @throws IllegalArgumentException if the provided user ID is null or the provided TODO item is null.
    * @throws IllegalStateException if the user with the given ID does not exist.
    * @throws Exception if an error occurs while creating the TODO item.
    * @PATH /api/user/:id/todo
    */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todo> createTodo(@PathVariable("id") Long userId, @RequestBody Todo todo) {
        //Optional<User> getuser= userService.get(userId); // Ensure the TODO item is linked to the user
        Todo createdTodo = todoService.save(todo, userId);
        return ResponseEntity.status(201).body(createdTodo);
    }

    /** Endpoint to update a TODO item by ID and user ID.
    * @param userId The ID of the user associated with the TODO item.
    * @param todoId The ID of the TODO item to update.
    * @param todo The updated TODO item. The user ID in the provided TODO item should match the provided user ID.
    * @return A ResponseEntity containing the updated TODO item with a 200 OK status if the TODO item is found and the user ID matches,
    *         or a 404 Not Found status if the TODO item is not found or does not belong to the specified user.
    * @throws IllegalArgumentException if the provided user ID or TODO item ID is null.
    * @throws IllegalStateException if the user with the given ID does not exist.
    * @throws Exception if an error occurs while updating the TODO item.
    * @PATH http://localhost:3718/api/user/:id/todo/:todoId
    */
    @PutMapping(value = "/{todoId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
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

    /** Endpoint to delete a TODO item by ID.
     * @param userId The ID of the user associated with the TODO item.
     * @param todoId The ID of the TODO item to delete.
     * @return A ResponseEntity with a 204 No Content status if the TODO item is found and the user ID matches,
     *         or a 404 Not Found status if the TODO item is not found or does not belong to the specified user.
     * @throws IllegalArgumentException if the provided user ID or TODO item ID is null.
     * @throws IllegalStateException if the user with the given ID does not exist.
     * @throws Exception if an error occurs while deleting the TODO item.
     * @PATH /api/user/:id/todo/:todoId
     */
    @DeleteMapping(value = "/{todoId}")
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