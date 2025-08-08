package ai.backend.infrastructure.gemini.services;

import ai.backend.domain.entities.Embedding;
import ai.backend.application.ports.EmbeddingService;
import com.google.genai.types.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;

import java.net.http.HttpClient;
import java.util.Optional;

@Service
public class GeminiEmbeddingService implements EmbeddingService {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeminiEmbeddingService(@Value("${GOOGLE_API_KEY}") String geminiApiKey) {
        this.geminiApiKey = geminiApiKey;
    }
    // API-kaldets URL og din API-nøgle
    private final String geminiApiKey;

    @Override
    public Embedding generateEmbedding(String text) {
            // Opret request body
        try (Client client =
                     Client.builder()
                             .apiKey(geminiApiKey)
                             .vertexAI(false)
                             .httpOptions(HttpOptions.builder().apiVersion("v1").build())
                             .build()) {

            EmbedContentResponse response =
                    client.models.embedContent("embedding-001", text, null);

            if (response.embeddings().isPresent())
            {
                var embeddings = response.embeddings().get();
                var embedding = embeddings.get(0);
                if (embedding.values().isPresent()) {
                    return new Embedding(embedding.values().get(), text);
                }

            }
           throw new RuntimeException("something went wrong");
        }
    }
}
