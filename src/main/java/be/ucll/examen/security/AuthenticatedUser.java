package be.ucll.examen.security;

import be.ucll.examen.entity.User;
import be.ucll.examen.repository.UserRepository;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class is responsible for handling authenticated user operations.
 * It provides methods to retrieve the authenticated user, logout the user, and clear JWT related cookies.
 */
@Component
public class AuthenticatedUser {

    @Autowired
    private UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private static final String JWT_HEADER_AND_PAYLOAD_COOKIE_NAME = "jwt.headerAndPayload";
    private static final String JWT_SIGNATURE_COOKIE_NAME = "jwt.signature";

    /** Constructor for AuthenticatedUser class.
     * @param authenticationContext The authentication context to handle authentication operations.
     * @param userRepository The user repository to retrieve user information.
     */
    public AuthenticatedUser(AuthenticationContext authenticationContext, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authenticationContext = authenticationContext;
    }
    /** Retrieves the authenticated user from the security context.
     * @return An Optional containing the authenticated user if present, otherwise an empty Optional.
     */
    private Optional<Authentication> getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return Optional.ofNullable(context.getAuthentication())
                .filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
    }
    /** Retrieves the authenticated user from the database using their username.
     * @return An Optional containing the authenticated user if present, otherwise an empty Optional.
     */
    public Optional<User> get() {
        return getAuthentication().map(authentication -> userRepository.findByUsername(authentication.getName()));
    }
    /** Logs out the authenticated user and clears all JWT related cookies.
     */
    public void logout() {
        authenticationContext.logout();
        clearCookies();
    }

    /** Clears all JWT (JSON Web Token) related cookies.
     * Specifically, it clears the JWT header and payload cookie and the JWT signature cookie.
     */
    private void clearCookies() {
        clearCookie(JWT_HEADER_AND_PAYLOAD_COOKIE_NAME);
        clearCookie(JWT_SIGNATURE_COOKIE_NAME);
    }

    /** Clears a cookie by setting its max age to 0 and adding it to the response.
     * @param cookieName The name of the cookie to clear.
     */
    private void clearCookie(String cookieName) {
        HttpServletRequest request = VaadinServletRequest.getCurrent()
                .getHttpServletRequest();
        HttpServletResponse response = VaadinServletResponse.getCurrent()
                .getHttpServletResponse();

        Cookie k = new Cookie(
                cookieName, null);
        k.setPath(getRequestContextPath(request));
        k.setMaxAge(0);
        k.setSecure(request.isSecure());
        k.setHttpOnly(false);
        response.addCookie(k);
    }

    /** This method retrieves the context path of the current request.
     * @param request The HttpServletRequest object representing the current request.
     * @return A string representing the context path of the request. If the context path is empty, it returns "/".
     */
    private String getRequestContextPath(HttpServletRequest request) {
        final String contextPath = request.getContextPath();
        return "".equals(contextPath) ? "/" : contextPath;
    }


}