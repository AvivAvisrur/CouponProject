package Exceptions;

public class CustomUserException extends Exception {
    /**
     * @param item the reason why the exception happend
     * @param entity the entity that got the exception
     */

    private String item;
    private String entity;

    public CustomUserException() {
        super();
    }

    public CustomUserException(String message) { super(message); }

    public CustomUserException(String item , String entity) {

        this.item = item;
        this.entity = entity;
    }

}
