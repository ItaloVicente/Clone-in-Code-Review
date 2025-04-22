    @Override
    public SerializableDocument newDocument(String id, int expiry, Serializable content, long cas,
        MutationToken mutationToken) {
        return SerializableDocument.create(id, expiry, content, cas, mutationToken);
    }

