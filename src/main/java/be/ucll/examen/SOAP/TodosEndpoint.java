package be.ucll.examen.SOAP;

import be.ucll.examen.SOAP.models.v1.*;
import be.ucll.examen.entity.Todo;
import be.ucll.examen.repository.*;
import be.ucll.examen.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

import static be.ucll.examen.SOAP.CONVERTERS.*;

/** This class represents a SOAP endpoint for managing Todos.
 * It handles SOAP requests to get and create Todos.
 * @author Thomas Vogelaers
 * @version 1.0
 */
@Endpoint
public class TodosEndpoint {
    private static final String NAMESPACE_URI = "http://todos.be/soap/todos";

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoService todoService;
    @Autowired
    private UserService UserService;


    /** This method handles the SOAP request to get a single Todo by its ID.
     * @param request The SOAP request containing the ID of the Todo to retrieve.
     * @return The SOAP response containing the requested Todo or an error message if the Todo is not found.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart= "getTodosRequest")
    @ResponsePayload
    public GetTodosResponse getTodo(@RequestPayload GetTodosRequest request) {
        GetTodosResponse response = new GetTodosResponse();
        Optional<Todo> todo = todoRepository.findById(request.getId());

        if (todo.isPresent()) {
            be.ucll.examen.SOAP.models.v1.Todo convertedTodo= convertToSoapTodo(todo.get());
            response.setType(STypeProcessOutcome.INFO);
            response.setTodo(convertedTodo);
            response.setErrormessage("No errors found");
        } else {
            response.setType(STypeProcessOutcome.ERROR);
            response.setTodo(null);
            response.setErrormessage("Todo not found");
        }
        return response;
    }

    /** This method handles the SOAP request to create a new Todo.
     * @param request The SOAP request containing the Todo data and the user ID.
     * @return The SOAP response containing the created Todo or an error message if the user is not found or if the Todo data is incomplete.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart= "createTodoRequest")
    @ResponsePayload
    public CreateTodoResponse createTodo(@RequestPayload CreateTodoRequest request) {
        CreateTodoResponse response = new CreateTodoResponse();

        Todo todo = convertToEntity(request.getTodo(), request.getUserId(), UserService);
        if (userRepository.findById(request.getUserId()).isEmpty()) {
            response.setType(STypeProcessOutcome.ERROR);
            response.setTodo(null);
            response.setErrorMessage("no user with that id was found!");
            return response;
        } else if (request.getTodo() == null || todo==null) {
            response.setType(STypeProcessOutcome.ERROR);
            response.setTodo(null);
            response.setErrorMessage("not all fields were specified! TODO IS NOT SAVED!");
            return response;
        } else {
            todo.setUser(userRepository.findById(request.getUserId()).get());
            todoService.save(todo,request.getUserId());
            response.setType(STypeProcessOutcome.INFO);
            response.setTodo(CONVERTERS.convertToSoapTodo(todo));
            response.setErrorMessage("Test succesfully created");
            return response;
        }
    }
}
