    @Override
    public BinaryDocument newDocument(String id, int expiry, ByteBuf content, long cas,
        MutationToken mutationToken) {
        return BinaryDocument.create(id, expiry, content, cas, mutationToken);
    }

