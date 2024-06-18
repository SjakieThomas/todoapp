package be.ucll.examen.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String s) {
        super(s);
        System.out.println(s);
    }
}
