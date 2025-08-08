package ai.backend.application.queries;

public interface QueryValidator< Q extends Query<R>, R> {
    void validate(Q query);
}
