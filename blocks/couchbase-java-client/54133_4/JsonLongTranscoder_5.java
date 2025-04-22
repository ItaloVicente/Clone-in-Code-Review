    @Override
    public JsonLongDocument newDocument(String id, int expiry, Long content, long cas,
        MutationToken mutationToken) {
        return JsonLongDocument.create(id, expiry, content, cas, mutationToken);
    }

