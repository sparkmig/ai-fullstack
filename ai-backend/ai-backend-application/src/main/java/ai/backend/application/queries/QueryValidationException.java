package ai.backend.application.queries;

public class QueryValidationException extends RuntimeException {
    public QueryValidationException(String message) {
        super(message);
    }
}
