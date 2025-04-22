    @Override
    public StringDocument newDocument(String id, int expiry, String content, long cas,
        MutationToken mutationToken) {
        return StringDocument.create(id, expiry, content, cas, mutationToken);
    }

