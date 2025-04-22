    /**
     * Creates a {@link JsonDocument} which the document id and JSON content.
     *
     * @param id the per-bucket unique document id.
     * @param content the content of the document.
     * @return a {@link JsonDocument}.
     */
    public static JsonDocument create(String id, JsonObject content) {
        return new JsonDocument(id, content, 0, 0, null);
    }
