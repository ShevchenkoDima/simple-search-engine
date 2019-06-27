package conductor.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conductor.model.Document;
import conductor.service.DocumentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DocumentService documentService;

    @Test
    public void getDocumentByKey() throws Exception {
        documentService.createDocument(new Document("document1", "My name is John"));
        mvc.perform(get("/documents/document1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("key", is("document1")))
                .andExpect(jsonPath("text", is("My name is John")));
    }

    @Test
    public void searchDocuments() throws Exception {
        documentService.createDocument(new Document("document1", "My name is John"));
        documentService.createDocument(new Document("document2", "My name is Michael"));

        mvc.perform(get("/documents/search/John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[document1]"))
                .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform(get("/documents/search/name")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[document1,document2]"))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllDocuments() throws Exception {
        documentService.createDocument(new Document("document1", "My name is John"));
        documentService.createDocument(new Document("document2", "My name is Michael"));

        mvc.perform(get("/documents")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text", is("My name is John")))
                .andExpect(jsonPath("$[0].key", is("document1")))
                .andExpect(jsonPath("$[1].text", is("My name is Michael")))
                .andExpect(jsonPath("$[1].key", is("document2")));
    }

}

