//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.21 at 08:46:38 PM CEST 
//


package be.ucll.examen.SOAP.models.v1;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the be.ucll.examen.SOAP.models.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: be.ucll.examen.SOAP.models.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTodosRequest }
     * 
     */
    public GetTodosRequest createGetTodosRequest() {
        return new GetTodosRequest();
    }

    /**
     * Create an instance of {@link GetTodosResponse }
     * 
     */
    public GetTodosResponse createGetTodosResponse() {
        return new GetTodosResponse();
    }

    /**
     * Create an instance of {@link TodoSoap }
     * 
     */
    public TodoSoap createTodoSoap() {
        return new TodoSoap();
    }

    /**
     * Create an instance of {@link UserSoap }
     * 
     */
    public UserSoap createUserSoap() {
        return new UserSoap();
    }

}