//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.22 at 02:14:53 AM CEST 
//


package be.ucll.examen.SOAP.models.v1;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for STypeProcessOutcome.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="STypeProcessOutcome"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="INFO"/&gt;
 *     &lt;enumeration value="WARNING"/&gt;
 *     &lt;enumeration value="ERROR"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "STypeProcessOutcome")
@XmlEnum
public enum STypeProcessOutcome {

    INFO,
    WARNING,
    ERROR;

    public String value() {
        return name();
    }

    public static STypeProcessOutcome fromValue(String v) {
        return valueOf(v);
    }

}
