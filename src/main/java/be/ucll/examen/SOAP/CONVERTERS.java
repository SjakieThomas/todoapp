package be.ucll.examen.SOAP;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import be.ucll.examen.SOAP.models.v1.Role;
import be.ucll.examen.SOAP.models.v1.TodoCreate;
import be.ucll.examen.entity.*;
import be.ucll.examen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * This class contains utility methods for converting between SOAP models and entity classes.
 */
public class CONVERTERS {

    /**
     * Converts an XMLGregorianCalendar to a LocalDate.
     *
     * @param xmlDate The XMLGregorianCalendar to convert.
     * @return The converted LocalDate, or null if the input was null.
     */
    public static LocalDate convertToLocalDate(XMLGregorianCalendar xmlDate) {
        if (xmlDate == null) {
            return null;
        }
        return xmlDate.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }
    /**
     * Converts a LocalDate to an XMLGregorianCalendar.
     *
     * @param localDate The LocalDate to convert.
     * @return The converted XMLGregorianCalendar, or null if the input was null.
     * @throws RuntimeException If a DatatypeConfigurationException occurs.
     */
    public static XMLGregorianCalendar convertToXMLGregorianCalendar(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Converts a SOAP Todo object to an entity Todo object.
     *
     * @param dto The SOAP Todo object to convert.
     * @return The converted entity Todo object.
     */
    public static Todo convertToEntity(be.ucll.examen.SOAP.models.v1.Todo dto) {
        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setTitle(dto.getTitle());
        todo.setComment(dto.getComment());
        todo.setCreationDate(convertToLocalDate(dto.getCreationDate()));
        todo.setDueDate(convertToLocalDate(dto.getDueDate()));
        todo.setStatus(convertToEntityStatus(dto.getTodoStatus()));
        todo.setTodoFor(convertToEntityFor(dto.getTodoFor()));
        todo.setUser(convertToUserEntity(dto.getUser()));
        return todo;
    }

    /**
     * Converts an entity Todo object to a SOAP Todo object.
     *
     * @param entityTodo The entity Todo object to convert.
     * @return The converted SOAP Todo object.
     */
    public static be.ucll.examen.SOAP.models.v1.Todo convertToSoapTodo(Todo entityTodo) {
        be.ucll.examen.SOAP.models.v1.Todo soapTodo = new be.ucll.examen.SOAP.models.v1.Todo();
        soapTodo.setId(entityTodo.getId());
        soapTodo.setTitle(entityTodo.getTitle());
        soapTodo.setComment(entityTodo.getComment());
        soapTodo.setCreationDate(convertToXMLGregorianCalendar(entityTodo.getCreationDate()));
        soapTodo.setDueDate(convertToXMLGregorianCalendar(entityTodo.getDueDate()));
        soapTodo.setTodoStatus(convertToSoapStatus(entityTodo.getStatus()));
        soapTodo.setTodoFor(convertToSoapFor(entityTodo.getTodoFor()));
        soapTodo.setUser(convertToSoapUser(entityTodo.getUser()));
        return soapTodo;
    }

    /**
     * Converts a SOAP TodoStatus enum to an entity TodoStatus enum.
     *
     * @param soapStatus The SOAP TodoStatus enum to convert.
     * @return The corresponding entity TodoStatus enum. If the input is null, returns null.
     *
     * @throws IllegalArgumentException If the input SOAP TodoStatus enum does not have a corresponding entity TodoStatus enum.
     */
    public static TodoStatus convertToEntityStatus(be.ucll.examen.SOAP.models.v1.TodoStatus soapStatus) {
        return soapStatus != null ? TodoStatus.valueOf(soapStatus.name()) : null;
    }
    /**
     * Converts an entity TodoStatus enum to a SOAP TodoStatus enum.
     *
     * @param entityStatus The entity TodoStatus enum to convert.
     * @return The corresponding SOAP TodoStatus enum. If the input is null, returns null.
     *
     * @throws IllegalArgumentException If the input entity TodoStatus enum does not have a corresponding SOAP TodoStatus enum.
     *
     * @see TodoStatus
     * @see be.ucll.examen.SOAP.models.v1.TodoStatus
     */
    public static be.ucll.examen.SOAP.models.v1.TodoStatus convertToSoapStatus(TodoStatus entityStatus) {
        return entityStatus != null ? be.ucll.examen.SOAP.models.v1.TodoStatus.valueOf(entityStatus.name()) : null;
    }


    /**
     * Converts a SOAP User object to an entity User object.
     *
     * @param soapUser The SOAP User object to convert.
     * @return The converted entity User object.
     */
    public static be.ucll.examen.entity.User convertToUserEntity(be.ucll.examen.SOAP.models.v1.User soapUser) {
        Set<be.ucll.examen.entity.Role> entityRoles = CONVERTERS.convertToEntityRoles(soapUser.getRoles());
        User userE =new User();
        userE.setId(soapUser.getId());
        userE.setUsername(soapUser.getUsername());
        userE.setFirstName(soapUser.getFirstname());
        userE.setLastName(soapUser.getLastname());
        userE.setEmail(soapUser.getEmail());
        userE.setPassword(soapUser.getPassword());
        userE.setRoles(entityRoles);
        return userE;
    }

    /**
     * Converts an entity User object to a SOAP User object.
     *
     * @param entityUser The entity User object to convert.
     * @return The converted SOAP User object.
     *
     * @throws NullPointerException If the input entityUser is null.
     *
     * @since 1.0
     */
    public static be.ucll.examen.SOAP.models.v1.User convertToSoapUser(User entityUser) {
        be.ucll.examen.SOAP.models.v1.User soapUser = new be.ucll.examen.SOAP.models.v1.User();
        soapUser.setId(entityUser.getId());
        soapUser.setUsername(entityUser.getUsername());
        soapUser.setFirstname(entityUser.getFirstName());
        soapUser.setLastname(entityUser.getLastName());
        soapUser.setEmail(entityUser.getEmail());
        soapUser.setPassword(entityUser.getPassword());
        return soapUser;
    }

    /**
     * Converts a SOAP TodoFor enum to an entity TodoFor enum.
     *
     * @param soapFor The SOAP TodoFor enum to convert.
     * @return The corresponding entity TodoFor enum. If the input is null, returns null.
     *
     * @throws IllegalArgumentException If the input SOAP TodoFor enum does not have a corresponding entity TodoFor enum.
     *
     * @since 1.0
     */
    public static TodoFor convertToEntityFor(be.ucll.examen.SOAP.models.v1.TodoFor soapFor) {
        return soapFor != null ? switch (soapFor) {
            case WERK -> TodoFor.WERK;
            case SCHOOL -> TodoFor.SCHOOL;
            case PRIVÉ -> TodoFor.PRIVÉ;
            case ANDERE -> TodoFor.ANDEREN;
        } : null;
    }
    /**
     * Converts an entity TodoFor enum to a SOAP TodoFor enum.
     *
     * @param entityFor The entity TodoFor enum to convert.
     * @return The corresponding SOAP TodoFor enum. If the input is null, returns null.
     *
     * @since 1.0
     */
    public static be.ucll.examen.SOAP.models.v1.TodoFor convertToSoapFor(TodoFor entityFor) {
        return entityFor != null ? switch (entityFor) {
            case WERK -> be.ucll.examen.SOAP.models.v1.TodoFor.WERK;
            case SCHOOL -> be.ucll.examen.SOAP.models.v1.TodoFor.SCHOOL;
            case PRIVÉ -> be.ucll.examen.SOAP.models.v1.TodoFor.PRIVÉ;
            case ANDEREN -> be.ucll.examen.SOAP.models.v1.TodoFor.ANDERE;
        } : null;
    }


    /**
     * Converts a Set of Role enums from be.ucll.examen.entity package to be.ucll.examen.SOAP.models.v1 package.
     * @param entityRoles The Set of Role enums from be.ucll.examen.entity package.
     * @return The corresponding Set of Role enums in be.ucll.examen.SOAP.models.v1 package.
     */
    public static List<Role> convertToSoapRoles(List<be.ucll.examen.entity.Role> entityRoles) {
        List<Role> soapRoles = new ArrayList<>();
        for (be.ucll.examen.entity.Role entityRole : entityRoles) {
            soapRoles.add(Role.fromValue(entityRole.name()));
        }
        return soapRoles;
    }

    /**
     * Converts a Set of Role enums from be.ucll.examen.SOAP.models.v1 package to be.ucll.examen.entity package.
     * @param soapRoles The Set of Role enums from be.ucll.examen.SOAP.models.v1 package.
     * @return The corresponding Set of Role enums in be.ucll.examen.entity package.
     */
    public static Set<be.ucll.examen.entity.Role> convertToEntityRoles(List<Role> soapRoles) {
        Set<be.ucll.examen.entity.Role> entityRoles = new HashSet<>();
        for (Role soapRole : soapRoles) {
            entityRoles.add(be.ucll.examen.entity.Role.valueOf(soapRole.value()));
        }
        return entityRoles;
    }



    public static Todo convertToEntity(TodoCreate dto, Long id,UserService userService) {
        Optional<User> user = userService.get(id);
        if(user.isPresent()) {
            Todo todo = new Todo();
            todo.setTitle(dto.getTitle());
            todo.setComment(dto.getComment());
            todo.setCreationDate(convertToLocalDate(dto.getCreationDate()));
            todo.setDueDate(convertToLocalDate(dto.getDueDate()));
            todo.setStatus(convertToEntityStatus(dto.getTodoStatus()));
            todo.setTodoFor(convertToEntityFor(dto.getTodoFor()));
            todo.setUser(user.get());
            return todo;
        } else {
            return null;
        }
    }


}
