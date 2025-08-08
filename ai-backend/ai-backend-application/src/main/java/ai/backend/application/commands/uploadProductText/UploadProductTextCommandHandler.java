package ai.backend.application.commands.uploadProductText;

import ai.backend.application.commands.CommandHandler;
import ai.backend.application.ports.EmbeddingService;
import ai.backend.application.ports.VectorDatabaseRepository;
import org.springframework.stereotype.Service;

@Service
public class UploadProductTextCommandHandler implements CommandHandler<UploadProductTextCommand> {
    final EmbeddingService embeddingService;
    final VectorDatabaseRepository vectorDatabaseRepository;

    public UploadProductTextCommandHandler(EmbeddingService embeddingService, VectorDatabaseRepository vectorDatabaseRepository) {
        this.embeddingService = embeddingService;
        this.vectorDatabaseRepository = vectorDatabaseRepository;
    }

    @Override
    public void handle(UploadProductTextCommand command) {
        var text = command.description();
        var embedding = embeddingService.generateEmbedding(text);
        vectorDatabaseRepository.insertEmbedding(embedding);
    }
}
