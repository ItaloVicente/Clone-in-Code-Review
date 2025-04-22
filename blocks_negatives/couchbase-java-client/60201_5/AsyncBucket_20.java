     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> Observable<DocumentFragment<T>> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);


    /**
     * Extend an array inside a {@link JsonDocument JSON document}, either pushing the new value at the
     * {@link ExtendDirection#FRONT front} or at the {@link ExtendDirection#BACK back}. The document id and path
     * inside the JSON where this mutation should happen are given by the {@link DocumentFragment}.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This Observable most notable error conditions are:
     *
     *  - The provided cas doesn't match the one of the enclosing document: {@link CASMismatchException}
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - There is no value at this location in the JSON: {@link PathNotFoundException}
     *  - The path isn't an array element (including if the path points to an array index): {@link PathMismatchException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the array to mutate and containing the new value to add to the array.
     * @param direction the position in the array where to insert (start/front of array or end/back of array).
     * @param createParents true if intermediate missing nodes in the path should also be created (effectively creating a new array).
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
