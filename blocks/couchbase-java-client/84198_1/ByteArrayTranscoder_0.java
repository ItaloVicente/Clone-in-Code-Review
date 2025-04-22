package com.couchbase.client.java.document;

import com.couchbase.client.core.message.kv.MutationToken;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ByteArrayDocument extends AbstractDocument<byte[]> implements Serializable {

    private static final long serialVersionUID = -8616443474645912439L;

    public static ByteArrayDocument create(String id) {
        return new ByteArrayDocument(id, 0, null, 0, null);
    }

    public static ByteArrayDocument create(String id, byte[] content) {
        return new ByteArrayDocument(id, 0, content, 0, null);
    }

    public static ByteArrayDocument create(String id, byte[] content, long cas) {
        return new ByteArrayDocument(id, 0, content, cas, null);
    }

    public static ByteArrayDocument create(String id, int expiry, byte[] content) {
        return new ByteArrayDocument(id, expiry, content, 0, null);
    }

    public static ByteArrayDocument create(String id, int expiry, byte[] content, long cas) {
        return new ByteArrayDocument(id, expiry, content, cas, null);
    }

    public static ByteArrayDocument create(String id, int expiry, byte[] content, long cas, MutationToken mutationToken) {
        return new ByteArrayDocument(id, expiry, content, cas, mutationToken);
    }

    public static ByteArrayDocument from(ByteArrayDocument doc, String id, byte[] content) {
        return ByteArrayDocument.create(id, doc.expiry(), content, doc.cas(), doc.mutationToken());
    }

    public static ByteArrayDocument from(ByteArrayDocument doc, long cas) {
        return ByteArrayDocument.create(doc.id(), doc.expiry(), doc.content(), cas, doc.mutationToken());
    }

    private ByteArrayDocument(String id, int expiry, byte[] content, long cas, MutationToken mutationToken) {
        super(id, expiry, content, cas, mutationToken);
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        writeToSerializedStream(stream);
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        readFromSerializedStream(stream);
    }
}
