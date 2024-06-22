package be.ucll.examen.views.login;

import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * This class represents the login view of the application.
 * It extends the LoginOverlay component from Vaadin Flow, which provides a login form.
 */
@PageTitle("login")
@Route(value = "login")
public class LoginView extends LoginOverlay {
    /** Constructor for LoginView class.
     * It initializes the login form with custom settings and i18n messages.
     */
    public LoginView() {
        setAction("login");
        //setAction(RouteUtil.getRoutePath(VaadinService.getCurrent().getContext(), getClass()));

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.setHeader(new LoginI18n.Header());
        i18n.getHeader().setTitle("todoapp");
        i18n.getHeader().setDescription("Login using user/user or admin/admin");
        i18n.setAdditionalInformation(null);
        setI18n(i18n);

        setForgotPasswordButtonVisible(false);
        setOpened(true);
    }


}
