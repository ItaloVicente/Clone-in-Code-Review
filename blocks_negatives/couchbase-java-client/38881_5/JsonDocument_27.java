    /**
     * Creates a {@link JsonDocument} which the document id.
     *
     * @param id the per-bucket unique document id.
     * @return a {@link JsonDocument}.
     */
    public static JsonDocument create(String id) {
        return new JsonDocument(id, null, 0, 0, null);
    }
