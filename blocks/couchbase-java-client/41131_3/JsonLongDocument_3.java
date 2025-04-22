package com.couchbase.client.java.document;

public class JsonDoubleDocument extends AbstractDocument<Double> {

    public static JsonDoubleDocument empty() {
        return new JsonDoubleDocument(null, 0, null, 0);
    }

    public static JsonDoubleDocument create(String id) {
        return new JsonDoubleDocument(id, 0, null, 0);
    }

    public static JsonDoubleDocument create(String id, Double content) {
        return new JsonDoubleDocument(id, 0, content, 0);
    }

    public static JsonDoubleDocument create(String id, Double content, long cas) {
        return new JsonDoubleDocument(id, 0, content, cas);
    }

    public static JsonDoubleDocument create(String id, int expiry, Double content) {
        return new JsonDoubleDocument(id, expiry, content, 0);
    }

    public static JsonDoubleDocument create(String id, int expiry, Double content, long cas) {
        return new JsonDoubleDocument(id, expiry, content, cas);
    }

    public static JsonDoubleDocument from(JsonDoubleDocument doc, String id) {
        return JsonDoubleDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static JsonDoubleDocument from(JsonDoubleDocument doc, Double content) {
        return JsonDoubleDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static JsonDoubleDocument from(JsonDoubleDocument doc, String id, Double content) {
        return JsonDoubleDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static JsonDoubleDocument from(JsonDoubleDocument doc, long cas) {
        return JsonDoubleDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private JsonDoubleDocument(String id, int expiry, Double content, long cas) {
        super(id, expiry, content, cas);
    }
}
