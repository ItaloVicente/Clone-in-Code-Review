package com.couchbase.client.java.document;

import java.io.Serializable;

public class SerializableDocument extends AbstractDocument<Serializable> {


    public static SerializableDocument empty() {
        return new SerializableDocument(null, 0, null, 0);
    }

    public static SerializableDocument create(String id) {
        return new SerializableDocument(id, 0, null, 0);
    }

    public static SerializableDocument create(String id, Serializable content) {
        return new SerializableDocument(id, 0, content, 0);
    }

    public static SerializableDocument create(String id, Serializable content, long cas) {
        return new SerializableDocument(id, 0, content, cas);
    }

    public static SerializableDocument create(String id, int expiry, Serializable content) {
        return new SerializableDocument(id, expiry, content, 0);
    }

    public static SerializableDocument create(String id, int expiry, Serializable content, long cas) {
        return new SerializableDocument(id, expiry, content, cas);
    }

    public static SerializableDocument from(SerializableDocument doc, String id) {
        return SerializableDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static SerializableDocument from(SerializableDocument doc, Long content) {
        return SerializableDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static SerializableDocument from(SerializableDocument doc, String id, Long content) {
        return SerializableDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static SerializableDocument from(SerializableDocument doc, long cas) {
        return SerializableDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private SerializableDocument(String id, int expiry, Serializable content, long cas) {
        super(id, expiry, content, cas);
    }
}
