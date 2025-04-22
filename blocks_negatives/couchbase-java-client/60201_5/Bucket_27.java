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
     * @param lookupSpecs the list of lookup operations to perform (use
     *        {@link LookupSpec#get(String) LookupSpec's static methods})
     * @return a {@link MultiLookupResult} representing the whole list of results (1 for each spec),
     * unless a document-level error happened (in which case an exception is propagated).
