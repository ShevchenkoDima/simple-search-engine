package conductor.cli;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class SimpleSearchEngineCliTest {

    private static final String DOCUMENT = "{\"key\":\"document1\",\"text\":\"My name is John\"}";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    @Test
    public void checkGetRequestToStubServerReturnsDocument() throws Exception {
        stubFor(get(urlEqualTo("/documents/document1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/json")
                        .withBody(DOCUMENT)));

        String response = SimpleSearchEngineCli.doRequest(new String[]{"-get", "document1"});

        Assert.assertEquals(DOCUMENT, response);
    }

    @Test
    public void checkPutRequestToStubServerReturnDocumentKey() throws Exception {
        stubFor(post(urlEqualTo("/documents/"))
                .withRequestBody(equalTo("key=document1&text=My+name+is+John"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/json")
                        .withBody("document1")));

        String response = SimpleSearchEngineCli.doRequest(
                new String[]{"-put", "document1", "My", "name", "is", "John"});

        Assert.assertEquals("document1", response);
    }

    @Test
    public void checkSearchRequestToStubServerReturnDocumentKeys() throws Exception {
        stubFor(get(urlEqualTo("/documents/search/John"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/json")
                        .withBody("[\"document1\"]")));

        String response = SimpleSearchEngineCli.doRequest(
                new String[]{"-search", "John"});

        Assert.assertEquals("[\"document1\"]", response);
    }
}