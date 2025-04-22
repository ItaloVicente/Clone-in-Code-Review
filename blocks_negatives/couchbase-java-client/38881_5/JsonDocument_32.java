    /**
     * Creates a copy from a different {@link JsonDocument}, but changes the document ID.
     *
     * @param doc the original {@link JsonDocument} to copy.
     * @param id the per-bucket unique document id.
     * @return a copied {@link JsonDocument} with the changed properties.
     */
    public static JsonDocument from(JsonDocument doc, String id) {
        return JsonDocument.create(id, doc.content(), doc.cas(), doc.expiry(), doc.status());
    }
