    @Override
    public RawJsonDocument newDocument(String id, int expiry, String content, long cas,
        MutationToken mutationToken) {
        return RawJsonDocument.create(id, expiry, content, cas, mutationToken);
    }

