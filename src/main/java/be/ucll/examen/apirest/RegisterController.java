package be.ucll.examen.apirest;


import be.ucll.examen.entity.User;
import be.ucll.examen.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    /* #TODO REGISTER POSTMAN:
     *   [V] GET
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [V] GET MULTIPLE
     *     [V] EX1
     *   [V] POST
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [V] PUT
     *     [V] EX1
     *     [V] EX2
     *     [V] EX3
     *   [V] DELETE
     *     [V] EX1
     * */



    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    //http://localhost:3718/api/register/:name
    public ResponseEntity<User> stringvoordegetmethode(@PathVariable String username) {
       Optional<User> user= userService.getUserByUsername(username);
       if (user.isPresent()) {
           return ResponseEntity.ok(user.get());
       }else {
            return ResponseEntity.status(404).body(null);
       }
    }
    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    //http://localhost:3718/api/register/:name
    public String getAll() {
        return "\n\n\n#--------------------------#\n" +
                "   There are "+ userService.count()+" users" +
                "\n#--------------------------#";
    }
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    //http://localhost:3718/api/register/
    public String addUser(@RequestBody User user) {
        userService.save(user);
        return user.getUsername()+" is toegevoegd";
    }

    @PutMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    //http://localhost:3718/api/register/
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
    @DeleteMapping(value="/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    //http://localhost:3718/api/register/
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
