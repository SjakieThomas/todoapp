package be.ucll.examen.SOAP;


import be.todos.soap.todos.Todo;
import be.todos.soap.todos.TodoFor;
import be.todos.soap.todos.TodoStatus;
import be.todos.soap.todos.User;
import be.ucll.examen.entity.Role;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class TodosRepositorySOAP {
    private static final Map<String, Todo> todos = new HashMap<>();

    public Todo findTodo(String title) {
        Assert.notNull(title, "title must not be null");
        return todos.get(title);
    }

    @PostConstruct
    public void init() {
        Set<Role> roles=new HashSet<>();
        roles.add(Role.USER);
        roles.add(Role.ADMIN);
        User user1 = new User();
        user1.setUsername("user1");
        user1.setId(1L);
        user1.setPassword("123");
        user1.setEmail("user1");
        user1.setFirstname("user1");
        user1.setLastname("user1");

        Todo todonr1 = new Todo();
        todonr1.setTitle("Todo 1");
        todonr1.setComment("Description 1");
        todonr1.setTodoStatus(TodoStatus.TODO);
        todonr1.setTodoFor(TodoFor.WERK);
        todonr1.setCreationDate(String.valueOf(java.time.LocalDate.now()));
        todonr1.setDueDate(String.valueOf(java.time.LocalDate.now().plusDays(6)));
        todonr1.setUser(user1);
        todos.put(todonr1.getTitle(), todonr1);

        Todo todonr2 = new Todo();
        todonr2.setTitle("Todo2");
        todonr2.setComment("Todo nr2 comment");
        todonr2.setTodoStatus(TodoStatus.DONE);
        todonr2.setTodoFor(TodoFor.WERK);
        todonr2.setCreationDate(String.valueOf(java.time.LocalDate.now()));
        todonr2.setDueDate(String.valueOf(java.time.LocalDate.now().plusDays(1)));
        todonr2.setUser(user1);
        todos.put(todonr2.getTitle(), todonr2);

        Todo todonr3 = new Todo();
        todonr3.setTitle("Todo3");
        todonr3.setComment("Todo nr3 this is a way longer comment");
        todonr3.setTodoStatus(TodoStatus.DONE);
        todonr3.setTodoFor(TodoFor.WERK);
        todonr3.setCreationDate(String.valueOf(java.time.LocalDate.now()));
        todonr3.setDueDate(String.valueOf(java.time.LocalDate.now().plusDays(13)));
        todonr3.setUser(user1);
        todos.put(todonr3.getTitle(), todonr3);


    }



}