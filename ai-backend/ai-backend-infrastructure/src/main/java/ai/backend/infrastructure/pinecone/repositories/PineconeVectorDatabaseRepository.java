package ai.backend.infrastructure.pinecone.repositories;

import ai.backend.application.ports.VectorDatabaseRepository;
import ai.backend.domain.entities.Embedding;
import com.google.protobuf.Struct;
import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class PineconeVectorDatabaseRepository implements VectorDatabaseRepository {

    private final String indexName;
    final Pinecone pinecone;

    public PineconeVectorDatabaseRepository(@Value("${PINECONE_API_KEY}") String pineconeApiKey, @Value("${PINECONE_INDEX}") String pineconeIndex) {
        this.pinecone = new Pinecone.Builder(pineconeApiKey).build();
        this.indexName = pineconeIndex;
    }
    @Override
    public void insertEmbedding(Embedding embedding) {
        Struct metaData = Struct.newBuilder()
                .putFields("text", com.google.protobuf.Value.newBuilder().setStringValue(embedding.getText()).build())
                .build();
        Index index = pinecone.getIndexConnection(indexName);

        index.upsert(embedding.getId(), embedding.getVector(), null, null, metaData, null);
    }

    @Override
    public String searchEmbeddings(Embedding embedding) {
        Index index = pinecone.getIndexConnection(indexName);

        var result = index.query(1, embedding.getVector(), null, null, null, "__default__", null, false, true);

        var bestMatch = result.getMatches(0);
        if (bestMatch == null) {
            return null;
        }

        var metaData = bestMatch.getMetadata();
        if (metaData == null) {
            return null;
        }

        var map = metaData.getFieldsMap();
        if (map.containsKey("text")) {
            return  map.get("text").getStringValue();
        }
        return null;
    }
}
