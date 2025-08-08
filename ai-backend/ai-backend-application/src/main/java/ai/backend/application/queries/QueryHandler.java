package ai.backend.application.queries;

public interface QueryHandler<Q extends Query<R>, R> {
    R handle(Q query);
}