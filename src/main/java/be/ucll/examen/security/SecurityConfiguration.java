package be.ucll.examen.security;

import be.ucll.examen.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** This class is responsible for configuring the security settings for the Vaadin application.
 * It extends VaadinWebSecurity to leverage its built-in security features.
 * @author Thomas Vogelaers
 * @version 1.0
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    /** The logout URL for the application. */
    public static final String LOGOUT_URL = "/logout";

//    @Value("${jwt.auth.secret}")
//    private String authSecret;

    /** Bean method to provide a password encoder.
     * In this case, we are using a NoOpPasswordEncoder for demonstration purposes.
     * In a production environment, you should use a more secure encoder like BCryptPasswordEncoder.
     * @return the password encoder*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return  NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }

    /** Overridden method to configure the HTTP security settings.
     * In this method, we are enabling security for the "/app/**" URL pattern.
     * We are also setting the login view to the LoginView class.
     * @param http the HttpSecurity object to configure
     * @throws Exception if an error occurs during configuration */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.securityMatcher("/app/**");    //comment this out to disable api endpoints and anable login
        super.configure(http);
        setLoginView(http, LoginView.class);
        //setStatelessAuthentication(http, new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256), "be.ucll.examen");

    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/register", "/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//
//        setLoginView(http, LoginView.class);
//    }


}