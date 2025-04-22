    /**
     * Runs a raw N1QL query and returns a {@link QueryResult} for each emitted row in the result.
     *
     * @param query the query.
     * @return
     */
    Observable<QueryResult> query(String query);
