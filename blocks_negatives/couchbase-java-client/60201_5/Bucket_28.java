    MultiLookupResult lookupIn(String id, LookupSpec... lookupSpecs);

    /**
     * Perform several {@link Lookup lookup} operations inside a single existing {@link JsonDocument JSON document}
     * with a custom timeout.
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
     * This method throws under the following notable error conditions:
     *
     *  - The operation takes longer than the specified timeout: {@link TimeoutException} wrapped in a {@link RuntimeException}
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
     * @param timeout the custom timeout.
     * @param timeUnit the unit for the timeout.
     * @param lookupSpecs the list of lookup operations to perform (use
     *        {@link LookupSpec#get(String) LookupSpec's static methods})
     * @return a {@link MultiLookupResult} representing the whole list of results (1 for each spec),
     * unless a document-level error happened (in which case an exception is propagated).
     */
    MultiLookupResult lookupIn(String id, long timeout, TimeUnit timeUnit, LookupSpec... lookupSpecs);

    /*-------------------------*
     * END OF SUB-DOCUMENT API *
     *-------------------------*/

