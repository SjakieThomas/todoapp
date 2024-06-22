//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.22 at 02:14:53 AM CEST 
//


package be.ucll.examen.SOAP.models.v1;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{http://todos.be/soap/todos}user"/&gt;
 *         &lt;element name="todo" type="{http://todos.be/soap/todos}Todo"/&gt;
 *         &lt;element name="type" type="{http://todos.be/soap/todos}STypeProcessOutcome"/&gt;
 *         &lt;element name="errorMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "user",
    "todo",
    "type",
    "errorMessage"
})
@XmlRootElement(name = "createTodoResponse")
public class CreateTodoResponse {

    @XmlElement(required = true)
    protected User user;
    @XmlElement(required = true)
    protected Todo todo;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected STypeProcessOutcome type;
    protected String errorMessage;

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setUser(User value) {
        this.user = value;
    }

    /**
     * Gets the value of the todo property.
     * 
     * @return
     *     possible object is
     *     {@link Todo }
     *     
     */
    public Todo getTodo() {
        return todo;
    }

    /**
     * Sets the value of the todo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Todo }
     *     
     */
    public void setTodo(Todo value) {
        this.todo = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link STypeProcessOutcome }
     *     
     */
    public STypeProcessOutcome getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link STypeProcessOutcome }
     *     
     */
    public void setType(STypeProcessOutcome value) {
        this.type = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorMessage(String value) {
        this.errorMessage = value;
    }

}
