//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.21 at 08:46:38 PM CEST 
//


package be.ucll.examen.SOAP.models.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for todo_for.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="todo_for"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SCHOOL"/&gt;
 *     &lt;enumeration value="WERK"/&gt;
 *     &lt;enumeration value="PRIVÉ"/&gt;
 *     &lt;enumeration value="ANDERE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "todo_for")
@XmlEnum
public enum TodoFor {

    SCHOOL,
    WERK,
    PRIVÉ,
    ANDERE;

    public String value() {
        return name();
    }

    public static TodoFor fromValue(String v) {
        return valueOf(v);
    }

}
