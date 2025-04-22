    /**
     * Create a new query with named parameters. Note that the {@link JsonObject}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *  @param statement the {@link Statement} to execute (containing named placeholders)
     * @param namedParams the values for the named placeholders in statement
     * @param params the {@link QueryParams query parameters}.
     */
    public ParametrizedQuery(Statement statement, JsonObject namedParams, QueryParams params) {
