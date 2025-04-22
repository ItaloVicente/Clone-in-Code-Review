    @Override
    public JsonDocument newDocument(String id, JsonObject content, long cas, int expiry, ResponseStatus status) {
        return JsonDocument.create(id, content, cas, expiry, status);
    }

    /**
