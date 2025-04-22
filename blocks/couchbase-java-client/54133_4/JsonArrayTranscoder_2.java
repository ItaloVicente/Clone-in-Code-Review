    @Override
    public JsonArrayDocument newDocument(String id, int expiry, JsonArray content, long cas,
        MutationToken mutationToken) {
        return JsonArrayDocument.create(id, expiry, content, cas, mutationToken);
    }

