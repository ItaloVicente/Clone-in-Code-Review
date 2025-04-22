package com.couchbase.client.java.document;

import com.couchbase.client.core.message.ResponseStatus;

public class BinaryDocument extends AbstractDocument<Object> {

    public static BinaryDocument empty() {
        return new BinaryDocument(null, null, 0, 0, null);
    }

    public static BinaryDocument create(final String id) {
        return new BinaryDocument(id, null, 0, 0, null);
    }

    public static BinaryDocument create(final String id, final Object content) {
        return new BinaryDocument(id, content, 0, 0, null);
    }

    public static BinaryDocument create(final String id, final Object content, final long cas) {
        return new BinaryDocument(id, content, cas, 0, null);
    }

    public static BinaryDocument create(final String id, final Object content, final int expiry) {
        return new BinaryDocument(id, content, 0, expiry, null);
    }

    public static BinaryDocument create(final String id, final Object content, final long cas, final int expiry, final ResponseStatus status) {
        return new BinaryDocument(id, content, cas, expiry, status);
    }

    public static BinaryDocument fromId(final BinaryDocument doc, final String id) {
        return BinaryDocument.create(id, doc.content(), doc.cas(), doc.expiry(), doc.status());
    }

    public static BinaryDocument from(final BinaryDocument doc, final Object content) {
        return BinaryDocument.create(doc.id(), content, doc.cas(), doc.expiry(), doc.status());
    }

    public static BinaryDocument from(final BinaryDocument doc, final String id, final Object content) {
        return BinaryDocument.create(id, content, doc.cas(), doc.expiry(), doc.status());
    }

    public static BinaryDocument from(final BinaryDocument doc, final long cas) {
        return BinaryDocument.create(doc.id(), doc.content(), cas, doc.expiry(), doc.status());
    }

    private BinaryDocument(final String id, final Object content, final long cas, final int expiry, final ResponseStatus status) {
        super(id, content, cas, expiry, status);
    }

}
