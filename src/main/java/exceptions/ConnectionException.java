package exceptions;

public final class ConnectionException extends RuntimeException {

    @java.io.Serial
    private static final long serialVersionUID = 1;

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
