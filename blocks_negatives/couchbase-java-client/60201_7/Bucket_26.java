     * Perform several {@link Lookup lookup} operations inside a single existing {@link JsonDocument JSON document}
     * with the default timeout.
     * The list of path to look for inside the JSON is represented through {@link LookupSpec LookupSpecs}.
     *
     * Each spec will receive an answer in the form of a {@link LookupResult}, meaning that if sub-document level
     * error conditions happen (like the path is malformed or doesn't exist), the error condition is still represented
     * as a LookupResult and the whole operation still succeeds.
     *
     * {@link MultiLookupResult} aggregates all these results and allows to check for such a partial failure (see
     * {@link MultiLookupResult#hasFailure()}, {@link MultiLookupResult#isTotalFailure()}, ...).
