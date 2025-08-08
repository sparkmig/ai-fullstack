package ai.backend.application.queries.searchProducts;

import ai.backend.application.ports.EmbeddingService;
import ai.backend.application.ports.VectorDatabaseRepository;
import ai.backend.application.queries.QueryHandler;
import org.springframework.stereotype.Service;

@Service
public class SearchProductsQueryHandler implements QueryHandler<SearchProductsQuery, String> {

    final VectorDatabaseRepository vectorDatabaseRepository;
    final EmbeddingService embeddingService;

    public SearchProductsQueryHandler(VectorDatabaseRepository vectorDatabaseRepository, EmbeddingService embeddingService) {
        this.vectorDatabaseRepository = vectorDatabaseRepository;
        this.embeddingService = embeddingService;
    }

    @Override
    public String handle(SearchProductsQuery query) {
        var embedded = embeddingService.generateEmbedding(query.getQuery());
        var result = vectorDatabaseRepository.searchEmbeddings(embedded);
        return result;
    }
}
