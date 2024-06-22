package be.ucll.examen;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point of the application.
 * This class is annotated with {@link SpringBootApplication} to enable Spring Boot auto-configuration.
 * It also implements {@link AppShellConfigurator} to define the application shell.
 * The application uses the Lumo dark theme by default, specified with {@link Theme}.
 */
@SpringBootApplication
@Theme(value = "todoapp", variant = Lumo.DARK)
public class Application implements AppShellConfigurator {

    /**
     * The main method to start the Spring Boot application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
