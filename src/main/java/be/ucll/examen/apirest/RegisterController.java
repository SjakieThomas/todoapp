package be.ucll.examen.apirest;


import be.ucll.examen.entity.User;
import be.ucll.examen.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value="/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String stringvoordegetmethode(@PathVariable String name) {
        System.out.println(name);
        return "helllo: "+name;
    }
    @PostMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void addUser(@RequestBody User user) {
        userService.save(user);
    }


}
