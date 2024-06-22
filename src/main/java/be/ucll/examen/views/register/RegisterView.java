package be.ucll.examen.views.register;

import be.ucll.examen.entity.Role;
import be.ucll.examen.entity.User;
import be.ucll.examen.services.UserService;
import be.ucll.examen.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Register")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class RegisterView extends Composite<VerticalLayout> {

    @Autowired
    private UserService userService;

    // Form fields
    private final TextField emailField = new TextField("Email");
    private final PasswordField passwordField = new PasswordField("Password");
    private final TextField firstNameField = new TextField("First Name");
    private final TextField lastNameField = new TextField("Last Name");
    private final TextField userNameField = new TextField("Username");

    public RegisterView() {
    //Layout main Content
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setAlignItems(Alignment.CENTER);
        getContent().setJustifyContentMode(JustifyContentMode.START);
    // Main layout column
        VerticalLayout layoutColumn2 = new VerticalLayout();
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setWidth("100%");
    // Title for the registration form
        H3 h3 = new H3("Register a new user");
        h3.setWidth("100%");
    // Form layout for the registration form 2 columns
        FormLayout formLayout2Col = new FormLayout();
        formLayout2Col.setWidth("100%");
    // Form layout for the registration form
        HorizontalLayout layoutRow = new HorizontalLayout();
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
    // register
        Button registerButton = new Button("Register");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(e -> registerUser());
        registerButton.setWidth("min-content");
    // cancelButton
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(e -> clearinput());
        cancelButton.setWidth("min-content");
    //add components to the layout
        getContent().add(layoutColumn2);
        layoutRow.add(registerButton,cancelButton);
        layoutColumn2.add(h3,formLayout2Col,layoutRow);
        formLayout2Col.add(firstNameField,lastNameField,emailField,passwordField,userNameField);
    }

    /** Registers a new user by collecting user input from the form fields,
     * creating a new User object with the provided data, and saving it to the database.
     * After saving the user, clears the form fields.
     */
    private void registerUser() {
        String email = emailField.getValue();
        String password = passwordField.getValue();
        String userName = userNameField.getValue();
        String lastName = lastNameField.getValue();
        String firstName = firstNameField.getValue();
        User user = new User(Role.USER,password,email,lastName,firstName,userName);
        userService.save(user);
        clearinput();
    }
    /** Clears the input fields of the registration form.
     * This method is called when the user clicks the "Cancel" button.
     * It resets all the form fields to their initial empty state.
     * @return void - This method does not return any value.
     */
    private void clearinput(){
        firstNameField.clear();
        lastNameField.clear();
        userNameField.clear();
        passwordField.clear();
        emailField.clear();
    }
}
