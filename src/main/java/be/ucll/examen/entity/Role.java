package be.ucll.examen.entity;


/** Enum representing different roles a user can have in the system.
 * <p>This enum is used to define the different roles that can be assigned to users in the application.
 * The roles are defined as follows:
 * <ul>
 *     <li>{@link #USER}: Represents a regular user of the system.</li>
 *     <li>{@link #ADMIN}: Represents an administrator user of the system, with additional privileges.</li>
 * </ul>
 * <p>The enum is used in various parts of the application to enforce role-based access control and ensure
 * that only authorized users can perform certain actions.
 */
public enum Role {
    USER, ADMIN;
}
