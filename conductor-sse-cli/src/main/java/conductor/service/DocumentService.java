package conductor.service;

import java.util.List;

public interface DocumentService {

    String getDocument(String key);

    String putDocument(String key, String document);

    String searchDocument(List<String> tokens);
}