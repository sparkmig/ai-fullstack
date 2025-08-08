package ai.backend.domain.entities;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class Embedding {
    private final List<Float> vector;
    private final String text;

    public Embedding(List<Float> vector, String text) {
        if (vector == null || vector.isEmpty()) {
            throw new IllegalArgumentException("Embedding vector cannot be empty.");
        }
        this.text = text;
        this.vector = vector;
    }

    public String getId() {
        return new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }

    public String getText() {
        return text;
    }

    public List<Float> getVector() {
        return vector;
    }
}
