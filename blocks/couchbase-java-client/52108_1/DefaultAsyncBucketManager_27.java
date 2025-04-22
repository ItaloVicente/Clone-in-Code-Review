    public Observable<DesignDocument> getDesignDocument(final String name, final boolean development) {
        return core.<GetDesignDocumentResponse>send(new RequestFactory() {
                @Override
                public CouchbaseRequest call() {
                    return new GetDesignDocumentRequest(name, development, bucket, password);
                }
            })
