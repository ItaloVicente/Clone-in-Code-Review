package com.couchbase.client.java.document;

public class RawJsonDocument extends AbstractDocument<String> {

    public static RawJsonDocument empty() {
        return new RawJsonDocument(null, 0, null, 0);
    }

    public static RawJsonDocument create(String id) {
        return new RawJsonDocument(id, 0, null, 0);
    }

    public static RawJsonDocument create(String id, String content) {
        return new RawJsonDocument(id, 0, content, 0);
    }

    public static RawJsonDocument create(String id, String content, long cas) {
        return new RawJsonDocument(id, 0, content, cas);
    }

    public static RawJsonDocument create(String id, int expiry, String content) {
        return new RawJsonDocument(id, expiry, content, 0);
    }

    public static RawJsonDocument create(String id, int expiry, String content, long cas) {
        return new RawJsonDocument(id, expiry, content, cas);
    }

    public static RawJsonDocument from(RawJsonDocument doc, String id, String content) {
        return RawJsonDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static RawJsonDocument from(RawJsonDocument doc, long cas) {
        return RawJsonDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private RawJsonDocument(String id, int expiry, String content, long cas) {
        super(id, expiry, content, cas);
    }

}
