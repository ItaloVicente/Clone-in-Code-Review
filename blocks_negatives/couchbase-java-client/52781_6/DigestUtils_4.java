public class QueryPlan implements SerializableStatement {

    private static final long serialVersionUID = 872622310459659115L;

    private final JsonObject jsonPlan;

    public QueryPlan(JsonObject jsonPlan) {
        this .jsonPlan = jsonPlan;
    }
