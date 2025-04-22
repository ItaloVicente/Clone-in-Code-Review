
    @Override
    public D newDocument(String id, int expiry, T content, long cas, MutationToken mutationToken) {
        throw new UnsupportedOperationException("This Transcoder does not support the "
            + "document creation with a mutation token (it needs to be implemented).");
    }
