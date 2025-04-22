    /**
     * Creates a {@link JsonDocument} which the document id, JSON content, CAS value, expiration time and status code.
     *
     * This factory method is normally only called within the client library when a response is analyzed and a document
     * is returned which is enriched with the status code. It does not make sense to pre populate the status field from
     * the user level code.
     *
     * @param id the per-bucket unique document id.
     * @param content the content of the document.
     * @param cas the CAS (compare and swap) value for optimistic concurrency.
     * @param expiry the expiration time of the document.
     * @param status the response status as returned by the underlying infrastructure.
     * @return a {@link JsonDocument}.
     */
    public static JsonDocument create(String id, JsonObject content, long cas, int expiry, ResponseStatus status) {
        return new JsonDocument(id, content, cas, expiry, status);
    }
