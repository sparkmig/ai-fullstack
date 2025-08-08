package ai.backend.application.queries.searchProducts;

import ai.backend.application.queries.Query;

public class SearchProductsQuery implements Query<String> {
    final String query;
    public SearchProductsQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}