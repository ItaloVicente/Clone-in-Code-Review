    public Observable<Boolean> removeDesignDocument(final String name, final boolean development) {
        return core.<RemoveDesignDocumentResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new RemoveDesignDocumentRequest(name, development, bucket, password);
                }
            })
