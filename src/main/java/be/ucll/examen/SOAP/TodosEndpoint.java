package be.ucll.examen.SOAP;



import be.ucll.examen.SOAP.models.v1.GetTodosRequest;
import be.ucll.examen.SOAP.models.v1.GetTodosResponse;
import be.ucll.examen.SOAP.models.v1.STypeProcessOutcome;
import be.ucll.examen.entity.Todo;
import be.ucll.examen.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.Console;
import java.util.Optional;

//@Component
@Endpoint
public class TodosEndpoint {
    private static final String NAMESPACE_URI = "http://todos.be/soap/todos";

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService UserService;
    @Autowired
    private TodoService todoService;


    /**
     * This method handles the SOAP request to get a single Todo by its ID.
     *
     * @param request The SOAP request containing the ID of the Todo to retrieve.
     * @return The SOAP response containing the requested Todo or an error message if the Todo is not found.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart= "getTodosRequest")
    @ResponsePayload
    public GetTodosResponse getTodo(@RequestPayload GetTodosRequest request) {
        // Create a new SOAP response object
        GetTodosResponse response = new GetTodosResponse();

        // Attempt to find the Todo in the database by its ID
        Optional<Todo> todo = todoRepository.findById(request.getId());

        // If the Todo is found, convert it to a SOAP Todo and set the response type to INFO
        if (todo.isPresent()) {
            be.ucll.examen.SOAP.models.v1.Todo convertedTodo= CONVERTERS.convertToSoapTodo(todo.get());
            response.setType(STypeProcessOutcome.INFO);
            response.setTodo(convertedTodo);
            response.setErrormessage("No errors found");

            return response;
        } else {
            // If the Todo is not found, set the response type to ERROR and provide an error message
            response.setType(STypeProcessOutcome.ERROR);
            response.setTodo(null);
            response.setErrormessage("Todo not found");
            return response;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart= "createTodoRequest")
    @ResponsePayload
    public CreateTodoResponse createTodo(@RequestPayload CreateTodoRequest request) {
        // Create a new SOAP response object
        CreateTodoResponse response = new CreateTodoResponse();

        Todo todo = CONVERTERS.convertToEntity(request.getTodo(), request.getUserId(), UserService);
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
