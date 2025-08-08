package ai.backend.application.queries.searchProducts;

import ai.backend.application.queries.QueryValidationException;
import ai.backend.application.queries.QueryValidator;
import org.springframework.stereotype.Component;

@Component
public class SearchProductsQueryValidator implements QueryValidator<SearchProductsQuery, String> {

    @Override
    public void validate(SearchProductsQuery query) {
        if (query == null || query.getQuery().isEmpty()) {
            throw new QueryValidationException("Empty query");
        }
    }
}
