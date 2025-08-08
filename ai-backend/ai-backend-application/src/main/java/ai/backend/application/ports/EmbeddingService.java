package ai.backend.application.ports;

import ai.backend.domain.entities.Embedding;

public interface EmbeddingService {
    Embedding generateEmbedding(String text);
}
