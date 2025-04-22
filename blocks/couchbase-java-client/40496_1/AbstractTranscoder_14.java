package com.couchbase.client.java.document;

import com.couchbase.client.core.message.ResponseStatus;

public class LegacyDocument extends AbstractDocument<Object> {


    public static LegacyDocument empty() {
        return new LegacyDocument(null, null, 0, 0, null);
    }

    public static LegacyDocument create(String id) {
        return new LegacyDocument(id, null, 0, 0, null);
    }

    public static LegacyDocument create(String id, Object content) {
        return new LegacyDocument(id, content, 0, 0, null);
    }

    public static LegacyDocument create(String id, Object content, long cas) {
        return new LegacyDocument(id, content, cas, 0, null);
    }

    public static LegacyDocument create(String id, Object content, int expiry) {
        return new LegacyDocument(id, content, 0, expiry, null);
    }

    public static LegacyDocument create(String id, Object content, long cas, int expiry, ResponseStatus status) {
        return new LegacyDocument(id, content, cas, expiry, status);
    }

    public static LegacyDocument from(LegacyDocument doc, String id) {
        return LegacyDocument.create(id, doc.content(), doc.cas(), doc.expiry(), doc.status());
    }

    public static LegacyDocument from(LegacyDocument doc, Object content) {
        return LegacyDocument.create(doc.id(), content, doc.cas(), doc.expiry(), doc.status());
    }

    public static LegacyDocument from(LegacyDocument doc, String id, Object content) {
        return LegacyDocument.create(id, content, doc.cas(), doc.expiry(), doc.status());
    }

    public static LegacyDocument from(LegacyDocument doc, long cas) {
        return LegacyDocument.create(doc.id(), doc.content(), cas, doc.expiry(), doc.status());
    }

    private LegacyDocument(String id, Object content, long cas, int expiry, ResponseStatus status) {
        super(id, content, cas, expiry, status);
    }
}
