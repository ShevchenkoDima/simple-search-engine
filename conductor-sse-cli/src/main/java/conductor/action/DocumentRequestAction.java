package conductor.action;

import conductor.service.DocumentService;

public interface DocumentRequestAction {

    String execute(DocumentService documentService);

}