package com.couchbase.client.java.document;

public class StringDocument extends AbstractDocument<String> {

    public static StringDocument empty() {
        return new StringDocument(null, 0, null, 0);
    }

    public static StringDocument create(String id) {
        return new StringDocument(id, 0, null, 0);
    }

    public static StringDocument create(String id, String content) {
        return new StringDocument(id, 0, content, 0);
    }

    public static StringDocument create(String id, String content, long cas) {
        return new StringDocument(id, 0, content, cas);
    }

    public static StringDocument create(String id, int expiry, String content) {
        return new StringDocument(id, expiry, content, 0);
    }

    public static StringDocument create(String id, int expiry, String content, long cas) {
        return new StringDocument(id, expiry, content, cas);
    }

    public static StringDocument from(StringDocument doc, String id, String content) {
        return StringDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static StringDocument from(StringDocument doc, long cas) {
        return StringDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private StringDocument(String id, int expiry, String content, long cas) {
        super(id, expiry, content, cas);
    }


}
