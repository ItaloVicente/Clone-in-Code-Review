    /**
     * Creates a {@link JsonDocument} which the document id, JSON content and the expiration time.
     *
     * @param id the per-bucket unique document id.
     * @param content the content of the document.
     * @param expiry the expiration time of the document.
     * @return a {@link JsonDocument}.
     */
    public static JsonDocument create(String id, JsonObject content, int expiry) {
        return new JsonDocument(id, content, 0, expiry, null);
    }
