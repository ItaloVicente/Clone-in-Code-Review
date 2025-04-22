
    /**
     * Create a new prepared query without parameters (the original statement shouldn't contain
     * parameter placeholders).
     *
     * @param payload the {@link PreparedPayload} to execute (containing no placeholders).
     */
    public static PreparedQuery prepared(PreparedPayload payload) {
        return new PreparedQuery(payload, null);
    }

    /**
     * Create a new prepared query with positionalParameters. Note that the {@link JsonArray}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *
     * @param payload the {@link PreparedPayload} to execute (containing positional placeholders).
     * @param positionalParams the values for the positional placeholders in statement.
     */
    public static PreparedQuery prepared(PreparedPayload payload, JsonArray positionalParams) {
        return new PreparedQuery(payload, positionalParams, null);
    }

    /**
     * Create a new prepared query with named parameters. Note that the {@link JsonObject}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *
     * @param payload the {@link PreparedPayload} to execute (containing named placeholders).
     * @param namedParams the values for the named placeholders in statement.
     */
    public static PreparedQuery prepared(PreparedPayload payload, JsonObject namedParams) {
        return new PreparedQuery(payload, namedParams, null);
    }

    /**
     * Create a new prepared query without parameters (the original statement shouldn't contain
     * parameter placeholders).
     *
     * @param payload the {@link PreparedPayload} to execute (containing no placeholders).
     * @param params the {@link QueryParams query parameters}.
     */
    public static PreparedQuery prepared(PreparedPayload payload, QueryParams params) {
        return new PreparedQuery(payload, params);
    }

    /**
     * Create a new prepared query with positionalParameters. Note that the {@link JsonArray}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *
     * @param payload the {@link PreparedPayload} to execute (containing no placeholders).
     * @param positionalParams the values for the positional placeholders in statement.
     * @param params the {@link QueryParams query parameters}.
     */
    public static PreparedQuery prepared(PreparedPayload payload, JsonArray positionalParams, QueryParams params) {
        return new PreparedQuery(payload, positionalParams, params);
    }

    /**
     * Create a new prepared query with named parameters. Note that the {@link JsonObject}
     * should not be mutated until {@link #n1ql()} is called since it backs the
     * creation of the query string.
     *
     * @param payload the {@link PreparedPayload} to execute (containing no placeholders).
     * @param namedParams the values for the named placeholders in statement.
     * @param params the {@link QueryParams query parameters}.
     */
    public static PreparedQuery prepared(PreparedPayload payload, JsonObject namedParams, QueryParams params) {
        return new PreparedQuery(payload, namedParams, params);
    }

