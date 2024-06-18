package be.ucll.examen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "todo")
public class Todo extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    private String comment;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    @Enumerated(EnumType.STRING)
    private TodoFor todoFor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public @NotNull LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(@NotNull LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public @NotNull LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(@NotNull LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public TodoFor getTodoFor() {
        return todoFor;
    }

    public void setTodoFor(TodoFor todoFor) {
        this.todoFor = todoFor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Todo() {}
    public Todo(Todo x) {
        this.id = x.getId();
        this.title = x.getTitle();
        this.comment = x.getComment();
        this.creationDate = x.getCreationDate();
        this.dueDate = x.getDueDate();
        this.status = x.getStatus();
        this.todoFor = x.getTodoFor();
        this.user = x.getUser();
    }
    public Todo(Long id, String title, String comment, LocalDate creationDate, LocalDate dueDate, TodoStatus status, TodoFor todoFor, User user) {
        this.id = id;
        this.title = title;
        this.comment = comment;
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.status = status;
        this.todoFor = todoFor;
        this.user = user;
    }
}
