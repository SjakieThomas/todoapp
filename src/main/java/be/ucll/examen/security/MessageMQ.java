package be.ucll.examen.security;

import be.ucll.examen.entity.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;



@Component
public class MessageMQ {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageMQ.class);

    @JmsListener(destination = "todos-queue")
    public void messageListener(Todo todo){
        LOGGER.info("Message received: {}", todo.toString());
    }


}
