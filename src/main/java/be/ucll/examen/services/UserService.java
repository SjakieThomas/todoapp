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

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public void save(User user) {
        repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(repository.findByUsername(username));
    }

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