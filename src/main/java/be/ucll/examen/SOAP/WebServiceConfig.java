package be.ucll.examen.SOAP;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/*** This class is responsible for configuring the SOAP web service in a Spring Boot application.
 * It extends the WsConfigurerAdapter to enable WS support and provides beans for the MessageDispatcherServlet,
 * DefaultWsdl11Definition, and XsdSchema.
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /** Creates a ServletRegistrationBean for the MessageDispatcherServlet.
     * This servlet handles incoming SOAP requests and dispatches them to the appropriate endpoint.
     * @param applicationContext The application context
     * @return The ServletRegistrationBean for the MessageDispatcherServlet
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /** Creates a DefaultWsdl11Definition bean for the WSDL definition.
     * This bean defines the port type, location URI, target namespace, and schema for the WSDL.
     * @param todosSchema The XsdSchema for the "todos" schema
     * @return The DefaultWsdl11Definition bean
     */
    @Bean(name = "todos")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema todosSchema) {
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setPortTypeName("TodosPort");
        definition.setLocationUri("/ws");
        definition.setTargetNamespace("http://todos.be/soap/todos");
        definition.setSchema(todosSchema);
        return definition;
    }

    /** Creates an XsdSchema bean for the "todos" schema.
     * This bean loads the schema from the "todos.xsd" file in the classpath.
     * @return The XsdSchema bean
     */
    @Bean
    public XsdSchema todosSchema() {
        return new SimpleXsdSchema(new ClassPathResource("todos.xsd"));
    }
}
