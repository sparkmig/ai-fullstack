package ai.backend.application.ports;

import ai.backend.domain.entities.Embedding;

public interface VectorDatabaseRepository {
    void insertEmbedding(Embedding embedding);
    String searchEmbeddings(Embedding embedding);
}
