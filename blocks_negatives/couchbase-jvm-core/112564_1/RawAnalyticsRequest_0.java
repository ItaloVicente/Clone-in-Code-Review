    /**
     * Create a {@link RawAnalyticsRequest} containing a full Analytics query in Json form (including additional
     * query parameters).
     *
     * The simplest form of such a query is a single statement encapsulated in a json query object:
     * <pre>{"statement":"SELECT * FROM default"}</pre>.
     *
     * @param jsonQuery the Analytics query in json form.
     * @param bucket the bucket on which to perform the query.
     * @param password the password for the target bucket.
     * @param targetNode the node on which to execute this request (or null to let the core locate and choose one).
     * @return a {@link RawAnalyticsRequest} for this full query.
     */
    public static RawAnalyticsRequest jsonQuery(String jsonQuery, String bucket, String password, InetAddress targetNode) {
        return new RawAnalyticsRequest(jsonQuery, bucket, bucket, password, targetNode, GenericAnalyticsRequest.NO_PRIORITY);
    }

