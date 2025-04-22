package com.couchbase.client.java.document;

public class JsonLongDocument extends AbstractDocument<Long> {

    public static JsonLongDocument empty() {
        return new JsonLongDocument(null, 0, null, 0);
    }

    public static JsonLongDocument create(String id) {
        return new JsonLongDocument(id, 0, null, 0);
    }

    public static JsonLongDocument create(String id, Long content) {
        return new JsonLongDocument(id, 0, content, 0);
    }

    public static JsonLongDocument create(String id, Long content, long cas) {
        return new JsonLongDocument(id, 0, content, cas);
    }

    public static JsonLongDocument create(String id, int expiry, Long content) {
        return new JsonLongDocument(id, expiry, content, 0);
    }

    public static JsonLongDocument create(String id, int expiry, Long content, long cas) {
        return new JsonLongDocument(id, expiry, content, cas);
    }

    public static JsonLongDocument from(JsonLongDocument doc, String id) {
        return JsonLongDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static JsonLongDocument from(JsonLongDocument doc, Long content) {
        return JsonLongDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static JsonLongDocument from(JsonLongDocument doc, String id, Long content) {
        return JsonLongDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static JsonLongDocument from(JsonLongDocument doc, long cas) {
        return JsonLongDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private JsonLongDocument(String id, int expiry, Long content, long cas) {
        super(id, expiry, content, cas);
    }
}
