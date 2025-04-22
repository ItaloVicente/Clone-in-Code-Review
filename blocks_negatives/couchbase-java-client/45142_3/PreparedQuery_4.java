    /**
     * Create a new prepared query without parameters (the original statement shouldn't contain
     * parameter placeholders).
     *
     * @param plan the prepared {@link QueryPlan} to execute (containing no placeholders).
     * @param params the {@link QueryParams query parameters}.
     */
    public PreparedQuery(QueryPlan plan, QueryParams params) {
