package Exceptions;

public class LengthException extends CustomUserException{
    /**
     * @param item the reason why the exception happend
     * @param entity the specific entity that got exception
     */
    private String item;
    private String entity;

    public LengthException() {
        super("Item already exist");
    }

    public LengthException(String message) { super(message); }

    public LengthException(String item , String entity) {
        this(String.format("%s you have exceeded the maximum length Reason: %s",entity, item));

        this.item = item;
        this.entity = entity;
    }


}
