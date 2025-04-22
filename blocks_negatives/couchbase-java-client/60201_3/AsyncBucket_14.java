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

    /**
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
     * Contrary to document-level {@link #upsert(Document)}, the document's CAS is actually taken into account if
     * provided in the fragment parameter, and the internal mutation will be rejected if the enclosing document was
     * externally modified.
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to mutate and containing the new value to apply.
     * @param createParents true
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
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

    /**
     * Replace a fragment of JSON with another one inside an existing {@link JsonDocument JSON document}. There should
     * already be a value at this location. The document id and path inside the JSON where this mutation should happen
     * are given by the {@link DocumentFragment}.
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
     */
    <T> Observable<DocumentFragment<T>> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Insert a value in an existing array inside a {@link JsonDocument JSON document}, at a specific index as denoted
     * by the path (eg. "some.array[2]"). Existing values at the left of the index are shifted forward.
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}. Index can be in the range 0-arraySize (inclusive).
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
     *  - The path denotes an existing array but with an index too large: {@link PathNotFoundException}
     *  - The path doesn't denote an array index: {@link PathInvalidException}
     *  - The array index is negative: {@link PathInvalidException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the array position at which to insert and containing
     *                 the new value to add to the array.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> Observable<DocumentFragment<T>> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Adds a value in an existing array inside a {@link JsonDocument JSON document} provided said value isn't
     * already present in the array (as checked using string comparison). This is restricted to primitive values
     * and arrays containing only primitives (no dictionary nor sub-array).
     *
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}.
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
     *  - The path doesn't denote an array: {@link PathMismatchException}
     *  - The array doesn't contain only primitive types: {@link PathMismatchException}
     *  - The value isn't primitive: {@link CannotInsertValueException}
     *  - The value is already in the array: {@link PathExistsException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the array in which to check and insert, containing
     *                 the new value to add to the array.
     * @param createParents set to true to create intermediary missing nodes and the array if it doesn't exist.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the mutated value, with
     * updated cas metadata.
     */
    <T> Observable<DocumentFragment<T>> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Removes an entry from an existing {@link JsonDocument JSON document}. This can be a scalar, whole dictionary or
     * array or specific dictionary entry or array index, as denoted by the path. The last element in an array can also
     * be removed by using the -1 index (eg. "some.array[-1]").
     *
     * The document id and path inside the JSON where this removal should happen are given by the
     * {@link DocumentFragment}.
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
     *  - An empty path was provided: {@link IllegalArgumentException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the path to remove (fragment value is ignored).
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @param <T> the type of the fragment value.
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the removed value, with
     * updated cas metadata but no content.
     */
    <T> Observable<DocumentFragment<T>> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Increment or decrement a numerical value inside an existing {@link JsonDocument JSON document}.
     * The document id and path inside the JSON where this should happen, and the delta to apply, are given by the
     * {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it is created and initialized with the delta.
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
     *  - The provided delta is zero: {@link ZeroDeltaException}
     *  - The delta would make the current value grow beyond {@link Long#MAX_VALUE}
     *    or below/at {@link Long#MIN_VALUE}: {@link DeltaTooBigException}
     *  - The current value is already over bounds (see above) due to external modification: {@link NumberTooBigException}
     *  - The path points to a value that isn't numeric: {@link PathMismatchException}
     *  - The durability constraint could not be fulfilled because of a temporary or persistent problem: {@link DurabilityException}
     *
     * Other error conditions include document-level error conditions (similar to those of {@link #replace(Document)})
     * and generic sub-document-level error conditions (all of which extend {@link SubDocumentException},
     * like {@link PathTooDeepException}).
     *
     * @param fragment a {@link DocumentFragment} pointing to the numerical value to increment/decrement
     *                 (fragment is the Long delta to apply) ignored).
     * @param createParents true if intermediary missing nodes in the path should be created.
     * @param persistTo the persistence constraint to watch (or NONE if not required).
     * @param replicateTo the replication constraint to watch (or NONE if not required).
     * @return an {@link Observable} emitting the {@link DocumentFragment} corresponding to the modified value (contains
     * the new value of the "counter", with updated cas metadata).
     */
    Observable<DocumentFragment<Long>> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Perform several {@link Lookup lookup} operations inside a single existing {@link JsonDocument JSON document}.
     * The list of path to look for inside the JSON is represented through {@link LookupSpec LookupSpecs}.
     *
     * Each spec will receive an answer in the form of a {@link LookupResult}, meaning that if sub-document level
     * error conditions happen (like the path is malformed or doesn't exist), the error condition is still represented
     * as a LookupResult and the whole operation still succeeds.
     *
     * {@link MultiLookupResult} aggregates all these results and allows to check for such a partial failure (see
     * {@link MultiLookupResult#hasFailure()}, {@link MultiLookupResult#isTotalFailure()}, ...).
     *
     * The subdocument API has the benefit of only transmitting the fragment of the document you work with
     * on the wire, instead of the whole document.
     *
     * This Observable most notable error conditions are:
     *
     *  - The enclosing document is not JSON: {@link DocumentNotJsonException}
     *  - The enclosing document does not exist: {@link DocumentDoesNotExistException}
     *  - The lookupSpecs vararg is null: {@link NullPointerException}
     *  - The lookupSpecs vararg is omitted/empty: {@link IllegalArgumentException}
     *
     * Each individual {@link LookupResult} can have errors denoted by a "SUBDOC_" error status (eg.
     * {@link ResponseStatus#SUBDOC_PATH_MISMATCH} or {@link ResponseStatus#SUBDOC_PATH_NOT_FOUND}),
     * in which case the value is null. For {@link Lookup#EXIST}, SUBDOC_PATH_NOT_FOUND is considered a "success" where
     * {@link LookupResult#exists()} returns false and {@link LookupResult#value()} returns false as well.
     *
     * If one prefers to deal with a {@link SubDocumentException} instead of the ResponseStatus, one can use
     * {@link LookupResult#valueOrThrow()}.
     *
     * One special fatal error can also happen that is represented as a LookupResult, when the value couldn't be decoded
     * from JSON. In that case, the ResponseStatus is {@link ResponseStatus#FAILURE} and the value() is a
     * {@link TranscodingException}.
     *
     * Other document-level error conditions are similar to those encountered during a document-level {@link #get(Document)}.
     *
     * @param id the id of the JSON document to look into.
     * @param lookupSpecs the list of lookup operations to perform (use
     *        {@link LookupSpec#get(String) LookupSpec's static methods})
     * @return an {@link Observable} of a single {@link MultiLookupResult} representing the whole list of results (1 for
     *        each spec), unless a document-level error happened (in which case an exception is propagated).
     */
    Observable<MultiLookupResult> lookupIn(String id, LookupSpec... lookupSpecs);

