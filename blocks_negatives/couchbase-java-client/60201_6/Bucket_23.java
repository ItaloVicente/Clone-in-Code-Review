    /*---------------------------*
     * START OF SUB-DOCUMENT API *
     *---------------------------*/
    /**
     * Retrieve a fragment of an existing {@link JsonDocument JSON document}, as denoted by the given path, with
     * the default timeout.
     * The {@link Observable} will be empty if the path could not be found inside the document.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * @return the {@link DocumentFragment} corresponding to the requested value, or null if the path could not be found.
     */
    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType);

    /**
     * Retrieve a fragment of an existing {@link JsonDocument JSON document}, as denoted by the given path, with
     * a custom timeout.
     * The {@link Observable} will be empty if the path could not be found inside the document.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the requested value, or null if the path could not be found.
     */
    <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType, long timeout, TimeUnit timeUnit);

    /**
     * Check for the existence of a path inside of a {@link JsonDocument JSON document}, with the default timeout.
     * For example "sub.some" will check that there is a JSON object called "sub" at the root of the document and
     * that it contains a "some" entry (which could be a scalar, array or another sub-object).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #get(Document)})
     * and some generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param id the JSON document's id to look into.
     * @param path the path to look for inside the JSON content.
     * @return <code>true</code> if the path could be found, <code>false</code> otherwise.
     */
    boolean existsIn(String id, String path);

    /**
     * Check for the existence of a path inside of a {@link JsonDocument JSON document}, with a custom timeout.
     * For example "sub.some" will check that there is a JSON object called "sub" at the root of the document and
     * that it contains a "some" entry (which could be a scalar, array or another sub-object).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #get(Document)})
     * and some generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param id the JSON document's id to look into.
     * @param path the path to look for inside the JSON content.
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @return <code>true</code> if the path could be found, <code>false</code> otherwise.
     */
    boolean existsIn(String id, String path, long timeout, TimeUnit timeUnit);

    /**
     * Upsert a fragment of JSON into an existing {@link JsonDocument JSON document}, with the default timeout.
     * The document id and path inside the JSON where this mutation should happen are given by the {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it will be created. Otherwise, the current value at
     * this location in the JSON is replaced by the one in the DocumentFragment.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * Contrary to document-level {@link #upsert(Document)}, the document's CAS is actually taken into account if
     * provided in the fragment parameter, and the internal mutation will be rejected if the enclosing document was
     * externally modified.
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param createParents true
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Upsert a fragment of JSON into an existing {@link JsonDocument JSON document}, with a custom timeout.
     * The document id and path inside the JSON where this mutation should happen are given by the {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it will be created. Otherwise, the current value at
     * this location in the JSON is replaced by the one in the DocumentFragment.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * Contrary to document-level {@link #upsert(Document)}, the document's CAS is actually taken into account if
     * provided in the fragment parameter, and the internal mutation will be rejected if the enclosing document was
     * externally modified.
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param createParents true
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Insert a fragment of JSON into an existing {@link JsonDocument JSON document}, on the condition that no value
     * already exist at this location, with a custom timeout. The document id and path inside the JSON where this
     * insertion should happen are given by the {@link DocumentFragment}.
     *
     * Unless <code>createParents</code> is set to true, only the last element of the path is created.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Insert a fragment of JSON into an existing {@link JsonDocument JSON document}, on the condition that no value
     * already exist at this location, with a custom timeout. The document id and path inside the JSON where this
     * insertion should happen are given by the {@link DocumentFragment}.
     *
     * Unless <code>createParents</code> is set to true, only the last element of the path is created.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Replace a fragment of JSON with another one inside an existing {@link JsonDocument JSON document}, with the
     * default timeout. There should already be a value at this location. The document id and path inside the JSON where
     * this mutation should happen are given by the {@link DocumentFragment}.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     *  - The provided cas doesn't match the one of the enclosing document: {@link CASMismatchException}
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - There is no value at this location in the JSON: {@link PathNotFoundException}
     *  - The path contains a node that is used as a wrong type (eg. "some.sub" where "some" is actually an array): {@link PathMismatchException}
     *  - The path ends at an array index (eg. "some.array[1]"): {@link PathInvalidException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Replace a fragment of JSON with another one inside an existing {@link JsonDocument JSON document}, with a custom
     * timeout. There should already be a value at this location. The document id and path inside the JSON where this
     * mutation should happen are given by the {@link DocumentFragment}.
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document. The returned {@link DocumentFragment} wraps the value and additional
     * metadata like a reminder of the document's id, the path, enclosing document's cas (or {@link MutationToken}).
     *
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
     *  - The provided cas doesn't match the one of the enclosing document: {@link CASMismatchException}
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - There is no value at this location in the JSON: {@link PathNotFoundException}
     *  - The path contains a node that is used as a wrong type (eg. "some.sub" where "some" is actually an array): {@link PathMismatchException}
     *  - The path ends at an array index (eg. "some.array[1]"): {@link PathInvalidException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

