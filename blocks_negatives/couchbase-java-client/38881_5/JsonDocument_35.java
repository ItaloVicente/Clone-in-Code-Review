    /**
     * Creates a copy from a different {@link JsonDocument}, but changes the CAS value.
     *
     * @param doc the original {@link JsonDocument} to copy.
     * @param cas the CAS (compare and swap) value for optimistic concurrency.
     * @return a copied {@link JsonDocument} with the changed properties.
     */
    public static JsonDocument from(JsonDocument doc, long cas) {
        return JsonDocument.create(doc.id(), doc.content(), cas, doc.expiry(), doc.status());
    }
