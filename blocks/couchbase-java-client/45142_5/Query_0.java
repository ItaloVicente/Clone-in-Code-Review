    public abstract JsonObject n1ql();

    public static SimpleQuery simple(Statement statement) {
        return new SimpleQuery(statement, null);
    }

    public static SimpleQuery simple(Statement statement, QueryParams params) {
        return new SimpleQuery(statement, params);
    }

    public static ParametrizedQuery parametrized(Statement statement, JsonArray positionalParams) {
        return new ParametrizedQuery(statement, positionalParams, null);
    }

    public static ParametrizedQuery parametrized(Statement statement, JsonObject namedParams) {
        return new ParametrizedQuery(statement, namedParams, null);
    }

    public static ParametrizedQuery parametrized(Statement statement, JsonArray positionalParams, QueryParams params) {
        return new ParametrizedQuery(statement, positionalParams, params);
    }

    public static ParametrizedQuery parametrized(Statement statement, JsonObject namedParams, QueryParams params) {
        return new ParametrizedQuery(statement, namedParams, params);
    }

    public static PreparedQuery prepared(QueryPlan plan) {
        return new PreparedQuery(plan, null);
    }

    public static PreparedQuery prepared(QueryPlan plan, JsonArray positionalParams) {
        return new PreparedQuery(plan, positionalParams, null);
    }

    public static PreparedQuery prepared(QueryPlan plan, JsonObject namedParams) {
        return new PreparedQuery(plan, namedParams, null);
    }

    public static PreparedQuery prepared(QueryPlan plan, QueryParams params) {
        return new PreparedQuery(plan, params);
    }

    public static PreparedQuery prepared(QueryPlan plan, JsonArray positionalParams, QueryParams params) {
        return new PreparedQuery(plan, positionalParams, params);
    }

    public static PreparedQuery prepared(QueryPlan plan, JsonObject namedParams, QueryParams params) {
        return new PreparedQuery(plan, namedParams, params);
    }
