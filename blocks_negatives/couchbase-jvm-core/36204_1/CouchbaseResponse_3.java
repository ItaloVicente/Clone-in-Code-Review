    /**
     * Get the underlying {@link Observable}.
     *
     * @return the observable which will complete the response.
     */
    Subject<CouchbaseResponse, CouchbaseResponse> observable();

    /**
     * Set the underlying {@link Observable} which will complete the associated response.
     *
     * @param observable the observable to complete later.
     * @return the {@link CouchbaseRequest} for chaining purposes.
     */
    CouchbaseResponse observable(Subject<CouchbaseResponse, CouchbaseResponse> observable);
