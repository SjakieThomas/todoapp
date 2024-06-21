package be.ucll.examen.SOAP;



//import be.ucll.examen.SOAP.models.v1.GetTodosRequest;
//import be.ucll.examen.SOAP.models.v1.GetTodosResponse;
//import SOAP.output.be.ucll.examen.SOAP.models.v1.GetTodosResponse;
import be.ucll.examen.SOAP.models.v1.GetTodosRequest;
import be.ucll.examen.SOAP.models.v1.GetTodosResponse;
import be.ucll.examen.SOAP.models.v1.TodoSoap;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

//@Component
@Endpoint
public class TodosEndpoint {
    private static final String NAMESPACE_URI = "http://todos.be/soap/todos";



    @PayloadRoot(namespace = NAMESPACE_URI, localPart= "getTodosRequest")
    @ResponsePayload
    public GetTodosResponse getTodoSoap(@RequestPayload GetTodosRequest request) {
        GetTodosResponse response = new GetTodosResponse();
        response.setTodoSoap(new TodoSoap());
        return response;
    }
}
