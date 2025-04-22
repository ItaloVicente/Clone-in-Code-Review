    @Override
    public JsonDocument newDocument(String id, int expiry, JsonObject content, long cas,
        MutationToken mutationToken) {
        return JsonDocument.create(id, expiry, content, cas, mutationToken);
    }

