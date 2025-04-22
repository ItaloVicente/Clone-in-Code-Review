    public PreparedQuery(QueryPlan plan, JsonObject namedParams) {
       super(plan, namedParams);
    }

    public PreparedQuery(QueryPlan plan) {
        super(plan, (JsonArray) null);
