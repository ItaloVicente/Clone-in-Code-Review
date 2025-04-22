    @Override
    public JsonBooleanDocument newDocument(String id, int expiry, Boolean content, long cas,
        MutationToken mutationToken) {
        return JsonBooleanDocument.create(id, expiry, content, cas, mutationToken);
    }

