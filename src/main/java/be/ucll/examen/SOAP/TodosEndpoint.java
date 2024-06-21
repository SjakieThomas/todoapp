package be.ucll.examen.SOAP;



import be.todos.soap.todos.GetTodosRequest;
import be.todos.soap.todos.GetTodosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class TodosEndpoint {
    private static final String NAMESPACE_URI = "http://www.ucll.be/examen";
    private TodosRepositorySOAP todosRepositorySOAP;

    @Autowired
    public TodosEndpoint(TodosRepositorySOAP todoRepositorySOAP) {
        this.todosRepositorySOAP = todoRepositorySOAP;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart= "getTodoRequest")
    @ResponsePayload
    public GetTodosResponse getTodo(@RequestPayload GetTodosRequest request) {
        GetTodosResponse response = new GetTodosResponse();
        response.setTodo(todosRepositorySOAP.findTodo(request.getTitle()));
        return response;
    }
}
