    /*---------------------------*
     * START OF SUB-DOCUMENT API *
     *---------------------------*/

    /**
     * Retrieve a fragment of an existing {@link JsonDocument JSON document}, as denoted by the given path.
     * The {@link Observable} will be empty if the path could not be found inside the document.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This Observable most notable error conditions are:
     *
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #get(Document)})
     * and some generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param id the JSON document's id to look into.
     * @param path the path to look for inside the JSON content.
     * @param fragmentType the type of the fragment value to be retrieved (so it can be directly casted).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the requested value,
     * or empty if the path could not be found.
     */
    <T> Observable<DocumentFragment<T>> getIn(String id, String path, Class<T> fragmentType);

    /**
     * Check for the existence of a path inside of a {@link JsonDocument JSON document}. For example "sub.some"
     * will check that there is a JSON object called "sub" at the root of the document and that it contains a
     * "some" entry (which could be a scalar, array or another sub-object).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This Observable most notable error conditions are:
     *
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #get(Document)})
     * and some generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param id the JSON document's id to look into.
     * @param path the path to look for inside the JSON content.
     * @return an {@link Observable} emitting <code>true</code> if the path could be found, <code>false</code> otherwise.
     */
    Observable<Boolean> existsIn(String id, String path);

