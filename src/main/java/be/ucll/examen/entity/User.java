package be.ucll.examen.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/** Represents a user in the application.
 * <p>This class is annotated with {@link Entity} to indicate that it is a JPA entity.
 * It is also annotated with {@link Table} to specify the name of the table in the database.
 */
@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    @Column(length = 1000000)
    private byte[] profilePicture;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference //om te voorkomen dat er een oneindig patroon is 418 im a teapot
    private List<Todo> todos = new ArrayList<>();

    public User() {}
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getFirstName() {return firstName;}
    public void setFirstName(String username) {this.firstName = username;}
    public String getLastName() {return lastName;}
    public void setLastName(String name) {this.lastName = name;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password=password;}
    public Set<Role> getRoles() {return roles;}
    public void setRoles(Set<Role> roles) {this.roles = roles;}
    public byte[] getProfilePicture() {return profilePicture;}
    public void setProfilePicture(byte[] profilePicture) {this.profilePicture = profilePicture;}
    public List<Todo> getTodos() {return todos;}
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    /** Constructs a new User object by copying the data from another User object.
     * @param user The User object from which to copy the data.
     * @throws NullPointerException If the provided user object is null.
     */
    public User(User user) {
        if (user == null) {
            throw new NullPointerException("User object cannot be null.");
        }
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRoles();
        this.profilePicture = user.getProfilePicture();
    }

    /** Constructs a new User object with the provided parameters.
     * @param roles The role of the user. It is set as a singleton in the Set.
     * @param password The password of the user.
     * @param email The email of the user.
     * @param lastName The last name of the user.
     * @param firstName The first name of the user.
     * @param username The username of the user.
     * @throws NullPointerException If any of the provided parameters are null.
     */
    public User(Role roles, String password, String email, String lastName, String firstName, String username) {
        if (roles == null || password == null || email == null || lastName == null || firstName == null || username == null) {
            throw new NullPointerException("None of the parameters can be null.");
        }
        this.roles = Collections.singleton(roles);
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
    }
}
