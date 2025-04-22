    /**
     * Creates a copy from a different {@link JsonDocument}, but changes the document ID and content.
     *
     * @param doc the original {@link JsonDocument} to copy.
     * @param id the per-bucket unique document id.
     * @param content the content of the document.
     * @return a copied {@link JsonDocument} with the changed properties.
     */
    public static JsonDocument from(JsonDocument doc, String id, JsonObject content) {
        return JsonDocument.create(id, content, doc.cas(), doc.expiry(), doc.status());
    }
