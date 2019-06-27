package conductor.model;

public class Document {

    private String key;
    private String text;

    public Document(String key, String text) {
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public String getText() {
        return text;
    }
}