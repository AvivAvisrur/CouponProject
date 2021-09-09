package Exceptions;

public class DuplicateException extends CustomUserException {
    /**
     * @param item the reason why the exception happend
     * @param entity the entity that got the exception
     */
    private String item;
    private String entity;

    public DuplicateException() {
        super("Item already exist");
    }

    public DuplicateException(String message) { super(message); }

    public DuplicateException(String item , String entity) {
        this(String.format("%s Duplication error Reason: %s",entity, item));

        this.item = item;
        this.entity = entity;
    }
}
