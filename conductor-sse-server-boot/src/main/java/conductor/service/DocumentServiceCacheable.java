package conductor.service;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import conductor.model.Document;

public class DocumentServiceCacheable implements DocumentService {

    private static final String DELIMITER = " ";

    private Map<String, Document> cache = new HashMap<>();

    @Override
    public Document getDocument(String id) {
        return cache.get(id);
    }

    @Override
    public List<String> searchDocuments(String tokens) {
        List<String> searchTokens = asList(tokens.split(DELIMITER));

        return cache.values().stream()
                .filter(d -> asList(d.getText().split(DELIMITER)).containsAll(searchTokens))
                .map(Document::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public String createDocument(Document document) {
        String key = document.getKey();
        cache.put(key, document);
        return key;
    }

    @Override
    public Collection<Document> getDocuments() {
        return cache.values();
    }
}