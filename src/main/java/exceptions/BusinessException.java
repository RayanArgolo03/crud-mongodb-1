package exceptions;

public final class BusinessException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 1;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
