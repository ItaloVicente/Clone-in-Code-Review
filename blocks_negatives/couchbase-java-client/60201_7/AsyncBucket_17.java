    <T> Observable<DocumentFragment<T>> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Insert a fragment of JSON into an existing {@link JsonDocument JSON document}, on the condition that no value
     * already exist at this location. The document id and path inside the JSON where this insertion should happen
     * are given by the {@link DocumentFragment}.
     *
     * Unless <code>createParents</code> is set to true, only the last element of the path is created.
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
     *  - The path doesn't exist entirely and <code>createParents</code> is set to false: {@link PathNotFoundException}.
     *  - The path contains a node that is used as a wrong type (eg. "some.sub" where "some" is actually an array): {@link PathMismatchException}
     *  - The path ends at an array index (eg. "some.array[1]"): {@link PathInvalidException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to insert.
     * @param createParents true
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> Observable<DocumentFragment<T>> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);
