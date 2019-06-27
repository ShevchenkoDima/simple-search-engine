package conductor.action;

import java.util.List;

import conductor.service.DocumentService;

public class SearchDocumentRequestAction implements DocumentRequestAction {

    private final List<String> tokens;

    public SearchDocumentRequestAction(List<String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String execute(DocumentService documentService) {
        return documentService.searchDocument(tokens);
    }
}