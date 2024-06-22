package be.ucll.examen.apirest;


import be.ucll.examen.entity.User;
import be.ucll.examen.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;



/** This class is a REST controller for handling user registration operations.
 * It provides endpoints for getting, adding, updating, and deleting users.
 * @author Thomas Vogelaers
 * @version 1.0
 */
@RestController
@RequestMapping("/api/register")
public class RegisterController {
    /** The UserService instance used for performing user-related operations.
     */
    private final UserService userService;
    /** Constructor for the RegisterController class.
     * @param userService The UserService instance to be used.
     */
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /** This method retrieves a user by their username.
     * @param username The username of the user to be retrieved.
     * @return A ResponseEntity containing the user if the user is found.
     *         If the user is not found, a ResponseEntity with a 404 status code and a null body is returned.
     * @apiNote This method is used to retrieve a user by their username.
     *          It is mapped to the GET request at "/api/register/{username}" endpoint.
     *          The response is a ResponseEntity containing the user.
     * @see UserService#getUserByUsername(String)
     * @Path /api/register/:name
     */
    @GetMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> stringvoordegetmethode(@PathVariable String username) {
       Optional<User> user= userService.getUserByUsername(username);
       if (user.isPresent()) {
           return ResponseEntity.ok(user.get());
       } else {
            return ResponseEntity.status(404).body(null);
       }
    }
    /**
     * This method returns a string representation of the total number of users.
     * @return A string containing the total number of users.
     * @apiNote This method is used to retrieve the total number of users.
     *          It is mapped to the GET request at "/api/register/" endpoint.
     *          The response is formatted as a string with a simple table-like structure.
     * @see UserService#count()
     */
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll() {
        return "\n\n\n#--------------------------#\n" +
                "   There are "+ userService.count()+" users" +
                "\n#--------------------------#";
    }
    /** This method adds a new user to the system.
     * @param user The user object to be added. The user object must be valid and not null.
     * @return A string indicating the success of the operation. The string will contain the username.
     * @apiNote This method is used to add a new user to the system.<br/>
     *          The response is a string indicating the success of the operation.
     * @Path "/api/register/"
     * @see UserService#save(User)
     */
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String addUser(@RequestBody User user) {
        userService.save(user);
        return user.getUsername()+" is toegevoegd";
    }
    /** Endpoint for updating a user by their username.
     * @param username The username of the user to be updated.
     * @param user The updated user object. The user object must be valid and not null.
     * @return A ResponseEntity containing the updated user if the user is found and updated successfully.
     *         If the user is not found, a ResponseEntity with a 404 status code and a null body is returned.
     * @apiNote This method is used to update an existing user in the system.
     *          The response is a ResponseEntity containing the updated user.
     * @Path "/api/register/(@username)"
     * @see UserService#getUserByUsername(String)
     * @see UserService#update(User)
     */
    @PutMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@PathVariable("username") String username,@RequestBody User user) {
        Optional<User> existingUser = userService.getUserByUsername(username);
        if (existingUser.isPresent() && existingUser.get().getUsername().equals(username)) {
            user.setId(existingUser.get().getId());
            User updatedUser = userService.update(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    /** Endpoint for deleting a user by their username.
     * @param username The username of the user to be deleted. This parameter is used to identify the user in the database.
     * @return A string.
     *          - If the user is found and deleted.
     *          - If the user is not found.
     * @apiNote This method is used to delete an existing user from the system.
     *          The response is a string indicating the success of the operation.
     * @Path "/api/register/(@username)"
     * @see UserService#getUserByUsername(String)
     * @see UserService#delete(Long)
     */
    @DeleteMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteUser(@PathVariable("username") String username) {
        Optional<User> existingUser = userService.getUserByUsername(username);
        if (existingUser.isPresent() && existingUser.get().getUsername().equals(username)) {
            userService.delete(existingUser.get().getId());
            return username+" is verwijderd";
        } else {
            return username+" is niet gevonden";
        }
    }

}
