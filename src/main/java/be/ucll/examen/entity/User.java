package be.ucll.examen.entity;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.password =  user.getPassword();
        this.roles = user.getRoles();
        this.profilePicture = user.getProfilePicture();
    }

    public User(Role roles, String password, String email, String lastName, String firstName, String username) {
        this.roles = Collections.singleton(roles);
        this.password = password;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
    }
}
