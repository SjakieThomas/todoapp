package be.ucll.examen.views;

import be.ucll.examen.entity.User;
import be.ucll.examen.security.AuthenticatedUser;
import be.ucll.examen.views.login.LoginView;
import be.ucll.examen.views.register.RegisterView;
import be.ucll.examen.views.todo.TODOView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import org.vaadin.lineawesome.LineAwesomeIcon;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@AnonymousAllowed
public class MainLayout extends AppLayout {

    /**
     * A simple navigation item component, based on ListItem element.
     */
    public static class MenuItemInfo extends ListItem {

        /**
         * The view class associated with this menu item.
         */
        private final Class<? extends Component> view;

        /** Constructor for MenuItemInfo.
         * @param menuTitle The title of the menu item.
         * @param icon      The icon to be displayed with the menu item.
         * @param view      The view class associated with this menu item.
         */
        public MenuItemInfo(String menuTitle, Component icon, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames(Display.FLEX, Gap.XSMALL, Height.MEDIUM, AlignItems.CENTER, Padding.Horizontal.SMALL,
                    TextColor.BODY);
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames(FontWeight.MEDIUM, FontSize.MEDIUM, Whitespace.NOWRAP);

            if (icon != null) {
                link.add(icon);
            }
            link.add(text);
            add(link);
        }

        /** Getter for the view class associated with this menu item.
         * @return The view class associated with this menu item.
         */
        public Class<?> getView() {
            return view;
        }

    }

    private final AuthenticatedUser authenticatedUser;
    private final AccessAnnotationChecker accessChecker;

    /** The constructor for the MainLayout class. It initializes the authenticatedUser and accessChecker fields,
     * and adds the created header content to the navbar of the AppLayout.
     * @param authenticatedUser The AuthenticatedUser object that provides access to the authenticated user's information.
     * @param accessChecker     The AccessAnnotationChecker object that checks access permissions for views.
     */
    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;
        addToNavbar(createHeaderContent());
    }

    /** This method creates and returns the header content for the main layout.
     * The header includes the application name, user avatar (if logged in), and navigation menu.
     * @return the header content component
     */
    private Component createHeaderContent() {
        Header header = new Header();
        header.addClassNames(BoxSizing.BORDER, Display.FLEX, FlexDirection.COLUMN, Width.FULL);

        Div layout = new Div();
        layout.addClassNames(Display.FLEX, AlignItems.CENTER, Padding.Horizontal.LARGE);

        H1 appName = new H1("todoapp");
        appName.addClassNames(Margin.Vertical.MEDIUM, Margin.End.AUTO, FontSize.LARGE);
        layout.add(appName);

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            Avatar avatar = new Avatar(user.getLastName());
            //StreamResource resource = new StreamResource("profile-pic",
            //       () -> new ByteArrayInputStream(user.getProfilePicture()));
            //avatar.setImageResource(resource);
            avatar.setThemeName("xsmall");
            avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(avatar);
            div.add(user.getLastName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        Nav nav = new Nav();
        nav.addClassNames(Display.FLEX, Overflow.AUTO, Padding.Horizontal.MEDIUM, Padding.Vertical.XSMALL);

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames(Display.FLEX, Gap.SMALL, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            if (accessChecker.hasAccess(menuItem.getView())) {
                list.add(menuItem);
            }
        }

        header.add(layout, nav);
        return header;
    }

    /**
     * This method creates an array of MenuItemInfo objects, each representing a navigation menu item.
     * The MenuItemInfo objects contain the menu title, icon, and the corresponding view class.
     *
     * @return an array of MenuItemInfo objects representing the navigation menu items
     */
    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{ //
                new MenuItemInfo("Register", LineAwesomeIcon.USER.create(), RegisterView.class), //

                new MenuItemInfo("TODO", LineAwesomeIcon.LIST_ALT_SOLID.create(), TODOView.class), //

                new MenuItemInfo("login", LineAwesomeIcon.PLUS_SOLID.create(), LoginView.class), //

        };
    }

    /**This method is called after a navigation event.
     * It overrides the default behavior of the AppLayout's afterNavigation method.
     * The overridden method is used to update the view title after navigation.
     * @see com.vaadin.flow.component.applayout.AppLayout#afterNavigation()*/
    @Override
    protected void afterNavigation() {
        super.afterNavigation();
    }
}
