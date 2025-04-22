package com.couchbase.client.java.document;

public class JsonBooleanDocument extends AbstractDocument<Boolean> {

    public static JsonBooleanDocument empty() {
        return new JsonBooleanDocument(null, 0, null, 0);
    }

    public static JsonBooleanDocument create(String id) {
        return new JsonBooleanDocument(id, 0, null, 0);
    }

    public static JsonBooleanDocument create(String id, Boolean content) {
        return new JsonBooleanDocument(id, 0, content, 0);
    }

    public static JsonBooleanDocument create(String id, Boolean content, long cas) {
        return new JsonBooleanDocument(id, 0, content, cas);
    }

    public static JsonBooleanDocument create(String id, int expiry, Boolean content) {
        return new JsonBooleanDocument(id, expiry, content, 0);
    }

    public static JsonBooleanDocument create(String id, int expiry, Boolean content, long cas) {
        return new JsonBooleanDocument(id, expiry, content, cas);
    }

    public static JsonBooleanDocument from(JsonBooleanDocument doc, String id) {
        return JsonBooleanDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static JsonBooleanDocument from(JsonBooleanDocument doc, Boolean content) {
        return JsonBooleanDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static JsonBooleanDocument from(JsonBooleanDocument doc, String id, Boolean content) {
        return JsonBooleanDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static JsonBooleanDocument from(JsonBooleanDocument doc, long cas) {
        return JsonBooleanDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private JsonBooleanDocument(String id, int expiry, Boolean content, long cas) {
        super(id, expiry, content, cas);
    }
}
