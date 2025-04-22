    /**
     * Create a new query with positionalParameters. Note that the {@link JsonArray}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *
     * @param statement the {@link Statement} to execute (containing positional placeholders)
     * @param positionalParams the values for the positional placeholders in statement
     * @param params the {@link QueryParams query parameters}.
     */
    public ParametrizedQuery(Statement statement, JsonArray positionalParams, QueryParams params) {
