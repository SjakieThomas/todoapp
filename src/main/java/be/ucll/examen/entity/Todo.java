package be.ucll.examen.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**Represents a Todo item in the application.
 * This class is annotated with {@link Entity} to indicate that it is a JPA entity.
 * It is also annotated with {@link Table} to specify the name of the table in the database.
 */
@Entity
@Table(name = "todo")
public class Todo extends AbstractEntity {

    /** The unique identifier of the Todo item.
     * It is annotated with {@link Id} to indicate that it is the primary key.
     * It is also annotated with {@link GeneratedValue} to specify that the value should be generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The title of the Todo item.
     * It is annotated with {@link NotBlank} to indicate that it cannot be null or empty.
     */
    @NotBlank
    private String title;

    /** The comment associated with the Todo item.
     */
    private String comment;

    /** The date when the Todo item was created.
     * It is annotated with {@link NotNull} to indicate that it cannot be null.
     */
    @NotNull
    private LocalDate creationDate;

    /** The due date of the Todo item.
     * It is annotated with {@link NotNull} to indicate that it cannot be null.
     */
    @NotNull
    private LocalDate dueDate;

    /** The status of the Todo item.
     * It is annotated with {@link Enumerated} to specify that it is an enum type.
     */
    @Enumerated(EnumType.STRING)
    private TodoStatus status;

    /** The type of the Todo item.
     * It is annotated with {@link Enumerated} to specify that it is an enum type.
     */
    @Enumerated(EnumType.STRING)
    private TodoFor todoFor;

    /** The user who created the Todo item.
     * It is annotated with {@link JsonBackReference} to prevent infinite recursion during JSON serialization.
     * It is also annotated with {@link ManyToOne} to indicate a many-to-one relationship.
     * It is annotated with {@link JoinColumn} to specify the foreign key column name.
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public @NotBlank String getTitle() {return title;}
    public void setTitle(@NotBlank String title) {this.title = title;}
    public String getComment() {return comment;}
    public void setComment(String comment) {this.comment = comment;}
    public @NotNull LocalDate getCreationDate() {return creationDate;}
    public void setCreationDate(@NotNull LocalDate creationDate) {this.creationDate = creationDate;}
    public @NotNull LocalDate getDueDate() {return dueDate;}
    public void setDueDate(@NotNull LocalDate dueDate) {this.dueDate = dueDate;}
    public TodoStatus getStatus() {return status;}
    public void setStatus(TodoStatus status) {this.status = status;}
    public TodoFor getTodoFor() {return todoFor;}
    public void setTodoFor(TodoFor todoFor) {this.todoFor = todoFor;}
    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    /** empty constructor for Todo class.
     */
    public Todo() {}

    /** Constructor for Todo class that creates a new Todo object by copying another Todo object.
     * @param x The Todo object to be copied.
     * @throws NullPointerException If the provided Todo object is null.
     */
    public Todo(Todo x) {
        if (x == null) {
            throw new NullPointerException("The provided Todo object cannot be null.");
        }
        this.id = x.getId();
        this.title = x.getTitle();
        this.comment = x.getComment();
        this.creationDate = x.getCreationDate();
        this.dueDate = x.getDueDate();
        this.status = x.getStatus();
        this.todoFor = x.getTodoFor();
        this.user = x.getUser();
    }

    /** Constructor for Todo class.
     * @param id           The unique identifier of the Todo item.
     * @param title        The title of the Todo item.
     * @param comment      The comment associated with the Todo item.
     * @param creationDate The date when the Todo item was created.
     * @param dueDate      The due date of the Todo item.
     * @param status       The status of the Todo item.
     * @param todoFor      The type of the Todo item.
     * @param user         The user who created the Todo item.
     */
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

    /** Overrides the toString method to provide a human-readable representation of the Todo object.
     * @return A string representation of the Todo object.
     */
    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                ", status=" + status +
                ", todoFor=" + todoFor +
                ", user=" + user +
                '}';
    }
}
