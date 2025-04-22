package com.couchbase.client.java.document;

import com.couchbase.client.java.document.json.JsonArray;

public class JsonArrayDocument extends AbstractDocument<JsonArray> {

    public static JsonArrayDocument empty() {
        return new JsonArrayDocument(null, 0, null, 0);
    }

    public static JsonArrayDocument create(String id) {
        return new JsonArrayDocument(id, 0, null, 0);
    }

    public static JsonArrayDocument create(String id, JsonArray content) {
        return new JsonArrayDocument(id, 0, content, 0);
    }

    public static JsonArrayDocument create(String id, JsonArray content, long cas) {
        return new JsonArrayDocument(id, 0, content, cas);
    }

    public static JsonArrayDocument create(String id, int expiry, JsonArray content) {
        return new JsonArrayDocument(id, expiry, content, 0);
    }

    public static JsonArrayDocument create(String id, int expiry, JsonArray content, long cas) {
        return new JsonArrayDocument(id, expiry, content, cas);
    }

    public static JsonArrayDocument from(JsonArrayDocument doc, String id) {
        return JsonArrayDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static JsonArrayDocument from(JsonArrayDocument doc, JsonArray content) {
        return JsonArrayDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static JsonArrayDocument from(JsonArrayDocument doc, String id, JsonArray content) {
        return JsonArrayDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static JsonArrayDocument from(JsonArrayDocument doc, long cas) {
        return JsonArrayDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private JsonArrayDocument(String id, int expiry, JsonArray content, long cas) {
        super(id, expiry, content, cas);
    }
}
