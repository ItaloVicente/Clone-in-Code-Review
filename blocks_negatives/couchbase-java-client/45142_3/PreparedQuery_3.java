    /**
     * Create a new prepared query with named parameters. Note that the {@link JsonObject}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *  @param plan the prepared {@link QueryPlan} to execute (containing named placeholders).
     * @param namedParams the values for the named placeholders in statement.
     * @param params the {@link QueryParams query parameters}.
     */
    public PreparedQuery(QueryPlan plan, JsonObject namedParams, QueryParams params) {
