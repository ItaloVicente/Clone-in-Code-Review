    @Override
    public JsonStringDocument newDocument(String id, int expiry, String content, long cas,
        MutationToken mutationToken) {
        return JsonStringDocument.create(id, expiry, content, cas, mutationToken);
    }

