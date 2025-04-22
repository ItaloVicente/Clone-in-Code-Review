
    public static LongDocument empty() {
        return new LongDocument(null, 0, null, 0);
    }

    public static LongDocument create(String id) {
        return new LongDocument(id, 0, null, 0);
    }

    public static LongDocument create(String id, Long content) {
        return new LongDocument(id, 0, content, 0);
    }

    public static LongDocument create(String id, Long content, long cas) {
        return new LongDocument(id, 0, content, cas);
    }

    public static LongDocument create(String id, int expiry, Long content) {
        return new LongDocument(id, expiry, content, 0);
    }

    public static LongDocument create(String id, int expiry, Long content, long cas) {
        return new LongDocument(id, expiry, content, cas);
    }

    public static LongDocument from(LongDocument doc, String id) {
        return LongDocument.create(id, doc.expiry(), doc.content(), doc.cas());
    }

    public static LongDocument from(LongDocument doc, Long content) {
        return LongDocument.create(doc.id(), doc.expiry(), content, doc.cas());
    }

    public static LongDocument from(LongDocument doc, String id, Long content) {
        return LongDocument.create(id, doc.expiry(), content, doc.cas());
    }

    public static LongDocument from(LongDocument doc, long cas) {
        return LongDocument.create(doc.id(), doc.expiry(), doc.content(), cas);
    }

    private LongDocument(String id, int expiry, Long content, long cas) {
