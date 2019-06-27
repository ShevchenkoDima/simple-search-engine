package conductor.action;

import conductor.service.DocumentService;

public class PutDocumentRequestAction implements DocumentRequestAction {

    private String key;
    private String document;

    public PutDocumentRequestAction(String key, String document) {
        this.key = key;
        this.document = document;
    }

    @Override
    public String execute(DocumentService documentService) {
        return documentService.putDocument(key, document);
    }
}