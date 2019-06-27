package conductor.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Collection;
import javax.validation.constraints.NotNull;

import conductor.service.DocumentService;
import conductor.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(method = GET)
    public Collection<Document> getAllDocuments() {
        return documentService.getDocuments();
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Document getDocument(@PathVariable @NotNull String id) {
        return documentService.getDocument(id);
    }

    @RequestMapping(method = POST)
    public String createDocument(@NotNull Document document) {
        return documentService.createDocument(document);
    }

    @RequestMapping(value = "/search/{tokens}", method = GET)
    public Collection<String> searchDocuments(@PathVariable @NotNull String tokens) {
        return documentService.searchDocuments(tokens);
    }

}
