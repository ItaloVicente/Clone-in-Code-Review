    /**
     * Create a {@link RawQueryRequest} containing a full N1QL query in Json form (including additional
     * query parameters like named arguments, etc...).
     *
     * The simplest form of such a query is a single statement encapsulated in a json query object:
     * <pre>{"statement":"SELECT * FROM default"}</pre>.
     *
     * @param jsonQuery the N1QL query in json form.
     * @param bucket the bucket on which to perform the query.
     * @param password the password for the target bucket.
     * @param targetNode the node on which to execute this request (or null to let the core locate and choose one).
     * @param contextId the context id to store and use for tracing purposes.
     * @return a {@link RawQueryRequest} for this full query.
     */
    public static RawQueryRequest jsonQuery(String jsonQuery, String bucket, String password, InetAddress targetNode, String contextId) {
        return new RawQueryRequest(jsonQuery, bucket, bucket, password, targetNode, contextId);
    }

