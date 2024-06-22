package be.ucll.examen.security;

import be.ucll.examen.entity.User;
import be.ucll.examen.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/** This class is responsible for providing user details for Spring Security authentication.
 * It implements the UserDetailsService interface, which is required by Spring Security.*/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /** The UserRepository interface for accessing user data.*/
    private final UserRepository userRepository;

    /** Constructor for UserDetailsServiceImpl.
     * @param userRepository the UserRepository interface for accessing user data */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** This method is called by Spring Security to load user details by username.
     * It retrieves the user from the database using the UserRepository and returns a Spring Security User object.
     * @param username the username of the user to load
     * @return a Spring Security User object containing the user's details
     * @throws UsernameNotFoundException if no user is found with the given username*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    getAuthorities(user));
        }
    }

    /** This method retrieves the authorities (roles) of a user and returns them as a list of GrantedAuthority objects.
     * @param user the user whose authorities need to be retrieved
     * @return a list of GrantedAuthority objects representing the user's roles */
    private static List<GrantedAuthority> getAuthorities(User user) {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
}