     * Upsert a fragment of JSON into an existing {@link JsonDocument JSON document}. The document id and path inside
     * the JSON where this mutation should happen are given by the {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it will be created. Otherwise, the current value at
     * this location in the JSON is replaced by the one in the DocumentFragment.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This Observable most notable error conditions are:
