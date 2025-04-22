    private static final long serialVersionUID = 872622310459659115L;

    private final JsonObject jsonPlan;

    public QueryPlan(JsonObject jsonPlan) {
        this .jsonPlan = jsonPlan;
    }

    /**
     * @return a String representation of the JSON for the execution plan.
     */
    @Override
    public String toString() {
        return jsonPlan.toString();
