     * Replace a fragment of JSON with another one inside an existing {@link JsonDocument JSON document}. There should
     * already be a value at this location. The document id and path inside the JSON where this mutation should happen
     * are given by the {@link DocumentFragment}.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This Observable most notable error conditions are:
