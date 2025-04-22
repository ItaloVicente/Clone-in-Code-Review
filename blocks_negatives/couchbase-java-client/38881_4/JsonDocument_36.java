    /**
     * Private constructor which is called by the static factory methods eventually.
     *
     * @param id the per-bucket unique document id.
     * @param content the content of the document.
     * @param cas the CAS (compare and swap) value for optimistic concurrency.
     * @param expiry the expiration time of the document.
     * @param status the response status as returned by the underlying infrastructure.
     */
    private JsonDocument(String id, JsonObject content, long cas, int expiry, ResponseStatus status) {
        super(id, content, cas, expiry, status);
    }
