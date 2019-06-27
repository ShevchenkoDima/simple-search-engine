package conductor.service;

import java.util.Collection;

import conductor.model.Document;

public interface DocumentService {

    Document getDocument(String id);

    Collection<String> searchDocuments(String tokens);

    String createDocument(Document document);

    Collection<Document> getDocuments();
}