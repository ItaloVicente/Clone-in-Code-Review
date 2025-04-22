    @Override
    public LegacyDocument newDocument(String id, int expiry, Object content, long cas,
        MutationToken mutationToken) {
        return LegacyDocument.create(id, expiry, content, cas, mutationToken);
    }

