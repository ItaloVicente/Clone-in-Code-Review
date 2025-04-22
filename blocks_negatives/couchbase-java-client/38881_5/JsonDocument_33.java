    /**
     * Creates a copy from a different {@link JsonDocument}, but changes the content.
     *
     * @param doc the original {@link JsonDocument} to copy.
     * @param content the content of the document.
     * @return a copied {@link JsonDocument} with the changed properties.
     */
    public static JsonDocument from(JsonDocument doc, JsonObject content) {
        return JsonDocument.create(doc.id(), content, doc.cas(), doc.expiry(), doc.status());
    }
