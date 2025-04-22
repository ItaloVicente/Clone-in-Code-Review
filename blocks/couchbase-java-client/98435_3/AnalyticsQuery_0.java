    public static ParameterizedAnalyticsQuery parameterized(final String statement,
        final JsonArray positionalParams) {
        return new ParameterizedAnalyticsQuery(statement, positionalParams, null, null);
    }

    public static ParameterizedAnalyticsQuery parameterized(final String statement,
        final JsonArray positionalParams, final AnalyticsParams params) {
        return new ParameterizedAnalyticsQuery(statement, positionalParams, null, params);
    }

    public static ParameterizedAnalyticsQuery parameterized(final String statement,
        final JsonObject namedParams) {
        return new ParameterizedAnalyticsQuery(statement, null, namedParams, null);
    }

    public static ParameterizedAnalyticsQuery parameterized(final String statement,
        final JsonObject namedParams, final AnalyticsParams params) {
        return new ParameterizedAnalyticsQuery(statement, null, namedParams, params);
    }
