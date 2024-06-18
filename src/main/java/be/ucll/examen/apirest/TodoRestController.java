package be.ucll.examen.apirest;


import be.ucll.examen.entity.Todo;
import be.ucll.examen.repository.TodoRepository;
import be.ucll.examen.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*      /TODO
*           aanmaken todo & Optionals
* */
@RestController
@RequestMapping("/api/todo")
public class TodoRestController {

    @Autowired
    private TodoRepository repository;
    @Autowired
    private TodoService service;
    /*
    /path user/{id}/todo/{idtodo}
     */


    @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Todo> findTodosByUsersId(@PathVariable("id") Long id){
        return service.findTodosByUserId(id);
    }
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Todo addTodo(@RequestBody Todo todo){
        //repository.createTodo(todo);
        return todo;
    }
}
