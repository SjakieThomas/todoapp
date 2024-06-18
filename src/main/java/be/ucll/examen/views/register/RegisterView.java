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
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Register")
@Route(value = "", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PermitAll
public class RegisterView extends Composite<VerticalLayout> {

    @Autowired
    private UserService userService;

    private TextField emailField;
    private PasswordField passwordField;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField userNameField;

    public RegisterView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H3 h3 = new H3();
        FormLayout formLayout2Col = new FormLayout();
        emailField = new TextField("Email");
        passwordField = new PasswordField("Password");
        firstNameField = new TextField("First Name");
        lastNameField = new TextField("Last Name");
        userNameField = new TextField("Username");
        HorizontalLayout layoutRow = new HorizontalLayout();
        Button registerButton = new Button("Register");
        Button cancelButton = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setAlignItems(Alignment.CENTER);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setMaxWidth("800px");
        layoutColumn2.setHeight("min-content");
        h3.setText("Register a new user");
        h3.setWidth("100%");
        formLayout2Col.setWidth("100%");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registerButton.addClickListener(e -> registerUser());
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        passwordField.setWidth("min-content");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.CENTER);
        registerButton.setWidth("min-content");
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancelButton.setWidth("min-content");
        getContent().add(layoutColumn2);
        layoutColumn2.add(h3);
        layoutColumn2.add(formLayout2Col);
        formLayout2Col.add(firstNameField);
        formLayout2Col.add(lastNameField);
        formLayout2Col.add(emailField);
        formLayout2Col.add(passwordField);
        formLayout2Col.add(userNameField);
        layoutColumn2.add(layoutRow);
        layoutRow.add(registerButton);
        layoutRow.add(cancelButton);

    }

    private void registerUser() {
        String email = emailField.getValue();
        String password = passwordField.getValue();
        String firstName = firstNameField.getValue();
        String lastName = lastNameField.getValue();
        String userName = userNameField.getValue();
        User user = new User(Role.USER, password,email, lastName,firstName,userName);
        userService.save(user);

        emailField.clear();
        passwordField.clear();
        firstNameField.clear();
        lastNameField.clear();
    }
}
