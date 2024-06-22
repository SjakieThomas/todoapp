package be.ucll.examen.services;

import be.ucll.examen.entity.User;
import be.ucll.examen.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

/** Service class for managing User entities.
 * @author Thomas Vogelaers
 * @version 1.0
 */
@Service
public class UserService {

    private final UserRepository repository;

    /** Constructor for UserService.
     * @param repository UserRepository for database operations.
     */
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    /** Retrieves a User by its ID.
     * @param id The ID of the User to retrieve.
     * @return An Optional containing the User if found, otherwise an empty Optional.
     */
    public Optional<User> get(Long id) {
        return repository.findById(id);
    }
    /** Saves a User to the database.
     * @param user The User to save.
     */
    public void save(User user) {
        repository.save(user);
    }
    /** Deletes a User from the database by its ID.
     * @param id The ID of the User to delete.
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }
    /** Retrieves a paginated list of Users.
     * @param pageable The Pageable object for pagination.
     * @return A Page containing the Users.
     */
    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }
    /** Retrieves a paginated list of Users based on a filter.
     * @param pageable The Pageable object for pagination.
     * @param filter The Specification for filtering.
     * @return A Page containing the filtered Users.
     */
    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }
    /** Retrieves the total count of Users in the database.
     * @return The total count of Users.
     */
    public int count() {
        return (int) repository.count();
    }
    /** Retrieves a User by its username.
     * @param username The username of the User to retrieve.
     * @return An Optional containing the User if found, otherwise an empty Optional.
     */
    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }
    /** Updates an existing User in the database.
     * @param newUser The updated User.
     * @return The updated User.
     * @throws EntityNotFoundException If the User with the given ID does not exist.
     */
    public User update(User newUser) {
        Optional<User> existingUserOptional = repository.findById(newUser.getId());

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            Optional.ofNullable(newUser.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(newUser.getFirstName()).ifPresent(existingUser::setFirstName);
            Optional.ofNullable(newUser.getLastName()).ifPresent(existingUser::setLastName);
            Optional.ofNullable(newUser.getEmail()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(newUser.getPassword()).ifPresent(existingUser::setPassword);
            Optional.ofNullable(newUser.getRoles()).ifPresent(existingUser::setRoles);
            Optional.ofNullable(newUser.getProfilePicture()).ifPresent(existingUser::setProfilePicture);
            return repository.save(existingUser);
        } else {
            throw new EntityNotFoundException("User not found with id " + newUser.getId());
        }
    }
}