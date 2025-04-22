    public static ParametrizedQuery parametrized(String statement, JsonArray positionalParams) {
        return new ParametrizedQuery(new RawStatement(statement), positionalParams, null);
    }

    public static ParametrizedQuery parametrized(String statement, JsonObject namedParams) {
        return new ParametrizedQuery(new RawStatement(statement), namedParams, null);
    }

    public static ParametrizedQuery parametrized(String statement, JsonArray positionalParams, QueryParams params) {
        return new ParametrizedQuery(new RawStatement(statement), positionalParams, params);
    }

    public static ParametrizedQuery parametrized(String statement, JsonObject namedParams, QueryParams params) {
        return new ParametrizedQuery(new RawStatement(statement), namedParams, params);
    }

