package conductor.service;

import static java.lang.String.join;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class DocumentHttpService implements DocumentService {

    private static final String DOCUMENTS_HOST = "http://localhost:8080/documents/";

    @Override
    public String getDocument(String key) {
        try {
            URI uri = URI.create(DOCUMENTS_HOST + key);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + con.getResponseCode());

            return readResponse(con);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public String putDocument(String key, String text) {
        try {
            URL url = new URL(DOCUMENTS_HOST);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write("key=" + URLEncoder.encode(key, "UTF-8") + "&text=" + URLEncoder.encode(text, "UTF-8"));
                writer.flush();
            }

            System.out.println("Sending 'POST' request to URL : " + url);
            System.out.println("Response Code : " + conn.getResponseCode());

            return readResponse(conn);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public String searchDocument(List<String> tokens) {
        try {
            URI uri = URI.create(DOCUMENTS_HOST + "search/" + URLEncoder.encode(join(" ", tokens), "UTF-8"));
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + con.getResponseCode());

            return readResponse(con);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private String readResponse(HttpURLConnection con) throws IOException {
        try (InputStream inputStream = con.getInputStream()) {
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        }
    }
}