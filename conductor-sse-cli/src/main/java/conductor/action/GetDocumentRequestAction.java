package conductor.action;

import conductor.service.DocumentService;

public class GetDocumentRequestAction implements DocumentRequestAction {

    private String key;

    public GetDocumentRequestAction(String key) {
        this.key = key;
    }

    @Override
    public String execute(DocumentService documentService) {
        return documentService.getDocument(key);
    }
}