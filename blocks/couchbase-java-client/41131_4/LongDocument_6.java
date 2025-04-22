package com.couchbase.client.java.document;

public class JsonStringDocument extends AbstractDocument<String> {

    public static JsonStringDocument empty() {
        return new JsonStringDocument(null, 0, null, 0);
    }

    public static JsonStringDocument create(String id) {
        return new JsonStringDocument(id, 0, null, 0);
    }

    public static JsonStringDocument create(String id, String content) {
        return new JsonStringDocument(id, 0, content, 0);
    }

    public static JsonStringDocument create(String id, String content, long cas) {
        return new JsonStringDocument(id, 0, content, cas);
    }

    public static JsonStringDocument create(String id, int expiry, String content) {
        return new JsonStringDocument(id, expiry, content, 0);
    }

    public static JsonStringDocument create(String id, int expiry, String content, long cas) {
        return new JsonStringDocument(id, expiry, content, cas);
    }

    public static JsonStringDocument from(JsonStringDocument doc, String id, String content) {
        return JsonStringDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static JsonStringDocument from(JsonStringDocument doc, long cas) {
        return JsonStringDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private JsonStringDocument(String id, int expiry, String content, long cas) {
        super(id, expiry, content, cas);
    }
}
