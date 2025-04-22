package com.couchbase.client.java.document;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;

public class BinaryDocument extends AbstractDocument<ByteBuf> {

    public static BinaryDocument empty() {
        return new BinaryDocument(null, 0, null, 0);
    }

    public static BinaryDocument create(String id) {
        return new BinaryDocument(id, 0, null, 0);
    }

    public static BinaryDocument create(String id, ByteBuf content) {
        return new BinaryDocument(id, 0, content, 0);
    }

    public static BinaryDocument create(String id, ByteBuf content, long cas) {
        return new BinaryDocument(id, 0, content, cas);
    }

    public static BinaryDocument create(String id, int expiry, ByteBuf content) {
        return new BinaryDocument(id, expiry, content, 0);
    }

    public static BinaryDocument create(String id, int expiry, ByteBuf content, long cas) {
        return new BinaryDocument(id, expiry, content, cas);
    }

    public static BinaryDocument from(BinaryDocument doc, String id, ByteBuf content) {
        return BinaryDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static BinaryDocument from(BinaryDocument doc, long cas) {
        return BinaryDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private BinaryDocument(String id, int expiry, ByteBuf content, long cas) {
        super(id, expiry, content, cas);
    }

}
