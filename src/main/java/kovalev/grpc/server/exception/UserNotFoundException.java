package kovalev.grpc.server.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException of(Class<?> clazz, Object field) {
        return new UserNotFoundException("Not found " + clazz + " with field " + field + " in DB");
    }
}
