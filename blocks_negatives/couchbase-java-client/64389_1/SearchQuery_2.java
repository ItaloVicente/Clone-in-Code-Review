    /**
     * Prepare an FTS {@link SearchQuery} on an index, without top-level query parameters.
     *
     * @param indexName the FTS index to search in.
     * @param queryPart the body of the FTS query (eg. a match phrase query).
     */
    public SearchQuery(String indexName, AbstractFtsQuery queryPart) {
        this(indexName, queryPart, null);
    }
