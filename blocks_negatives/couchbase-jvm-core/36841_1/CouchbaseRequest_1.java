    /**
     * Set the underlying {@link Observable} which will complete the associated response.
     *
     * @param observable the observable to complete later.
     * @return the {@link CouchbaseRequest} for chaining purposes.
     */
    CouchbaseRequest observable(Subject<CouchbaseResponse, CouchbaseResponse> observable);

