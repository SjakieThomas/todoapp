package be.ucll.examen.repository;


import be.ucll.examen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

/**
 * This interface represents a repository for managing User entities.
 * It extends JpaRepository and JpaSpecificationExecutor, providing basic CRUD operations and
 * the ability to execute JPA specifications.
 *
 * @author Thomas Vogelaers
 * @version 1.0
 * @since 2024-06-08 18:34
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * Finds a User by their username.
     *
     * @param username the username to search for
     * @return the User with the given username, or null if not found
     */
    User findByUsername(String username);

}