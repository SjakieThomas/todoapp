package be.ucll.examen.entity;

/** Enum representing different statuses of tasks.
 * <p>This enum is used to track the progress of tasks and ensure that they are handled correctly.
 * The statuses are defined as follows:
 * <ul>
 *     <li>{@link #TODO}: Represents tasks that need to be done.</li>
 *     <li>{@link #IN_PROGRESS}: Represents tasks that are currently being worked on.</li>
 *     <li>{@link #DONE}: Represents tasks that have been completed.</li>
 * </ul>
 */
public enum TodoStatus {
    TODO,
    IN_PROGRESS,
    DONE
}