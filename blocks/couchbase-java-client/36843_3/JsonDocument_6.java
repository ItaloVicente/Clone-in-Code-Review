    public static JsonDocument create(String id, JsonObject content, long cas, int expiry, ResponseStatus status) {
        return new JsonDocument(id, content, cas, expiry, status);
    }

    public static JsonDocument from(JsonDocument doc, String id) {
        return JsonDocument.create(id, doc.content(), doc.cas(), doc.expiry(), doc.status());
    }

    public static JsonDocument from(JsonDocument doc, JsonObject content) {
        return JsonDocument.create(doc.id(), content, doc.cas(), doc.expiry(), doc.status());
    }

    public static JsonDocument from(JsonDocument doc, String id, JsonObject content) {
        return JsonDocument.create(id, content, doc.cas(), doc.expiry(), doc.status());
    }

    public static JsonDocument from(JsonDocument doc, long cas) {
        return JsonDocument.create(doc.id(), doc.content(), cas, doc.expiry(), doc.status());
    }

    private JsonDocument(String id, JsonObject content, long cas, int expiry, ResponseStatus status) {
        super(id, content, cas, expiry, status);
    }
