    @Override
    public JsonDoubleDocument newDocument(String id, int expiry, Double content, long cas,
        MutationToken mutationToken) {
        return JsonDoubleDocument.create(id, expiry, content, cas, mutationToken);
    }

