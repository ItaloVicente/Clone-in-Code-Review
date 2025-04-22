    <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Extend an array inside a {@link JsonDocument JSON document}, either pushing the new value at the
     * {@link ExtendDirection#FRONT front} or at the {@link ExtendDirection#BACK back}, with a custom timeout.
     * The document id and path inside the JSON where this mutation should happen are given by the {@link DocumentFragment}.
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Insert a value in an existing array inside a {@link JsonDocument JSON document}, at a specific index as denoted
     * by the path (eg. "some.array[2]"), with the default timeout. Existing values at the left of the index are shifted forward.
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}. Index can be in the range 0-arraySize (inclusive).
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
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Insert a value in an existing array inside a {@link JsonDocument JSON document}, at a specific index as denoted
     * by the path (eg. "some.array[2]"), with a custom timeout. Existing values at the left of the index are shifted forward.
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}. Index can be in the range 0-arraySize (inclusive).
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Adds a value in an existing array inside a {@link JsonDocument JSON document}, with the default timeout.
     * This is provided said value isn't already present in the array (as checked using string comparison).
     * This is restricted to primitive values and arrays containing only primitives (no dictionary nor sub-array).
     *
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}.
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
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Adds a value in an existing array inside a {@link JsonDocument JSON document}, with a custom timeout.
     * This is provided said value isn't already present in the array (as checked using string comparison).
     * This is restricted to primitive values and arrays containing only primitives (no dictionary nor sub-array).
     *
     * The document id and path inside the JSON where this mutation should happen are given by the
     * {@link DocumentFragment}.
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the mutated value, with updated cas metadata.
     */
    <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Removes an entry from an existing {@link JsonDocument JSON document}, with the default timeout.
     * This can be a scalar, whole dictionary or array or specific dictionary entry or array index,
     * as denoted by the path. The last element in an array can also be removed by using the -1 index
     * (eg. "some.array[-1]").
     *
     * The document id and path inside the JSON where this removal should happen are given by the
     * {@link DocumentFragment}.
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
     * @return the {@link DocumentFragment} corresponding to the removed value, with updated cas metadata but no content.
     */
    <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Removes an entry from an existing {@link JsonDocument JSON document}, with a custom timeout.
     * This can be a scalar, whole dictionary or array or specific dictionary entry or array index,
     * as denoted by the path. The last element in an array can also be removed by using the -1 index
     * (eg. "some.array[-1]").
     *
     * The document id and path inside the JSON where this removal should happen are given by the
     * {@link DocumentFragment}.
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param <T> the type of the fragment value.
     * @return the {@link DocumentFragment} corresponding to the removed value, with updated cas metadata but no content.
     */
    <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);

    /**
     * Increment or decrement a numerical value inside an existing {@link JsonDocument JSON document} with the default
     * timeout.
     * The document id and path inside the JSON where this should happen, and the delta to apply, are given by the
     * {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it is created and initialized with the delta.
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
     * @return a {@link DocumentFragment} corresponding to the modified value (contains the new value of the "counter",
     * with updated cas metadata).
     */
    DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo);

    /**
     * Increment or decrement a numerical value inside an existing {@link JsonDocument JSON document} with a custom
     * timeout.
     * The document id and path inside the JSON where this should happen, and the delta to apply, are given by the
     * {@link DocumentFragment}.
     *
     * If the last element of the path doesn't exist, it is created and initialized with the delta.
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @return a {@link DocumentFragment} corresponding to the modified value (contains the new value of the "counter",
     * with updated cas metadata).
     */
    DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit);
