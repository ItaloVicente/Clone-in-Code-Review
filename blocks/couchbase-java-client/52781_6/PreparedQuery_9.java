    public PreparedPayload statement() {
        return (PreparedPayload) super.statement();
    }

    @Override
    public JsonObject n1ql() {
        JsonObject n1ql = super.n1ql();
        String preparePrefix = "PREPARE " + statement().preparedName() + " ";
        String prepareFallback = statement().originalStatement().toString();
        if (!prepareFallback.toLowerCase().startsWith("prepare ")) {
            prepareFallback = preparePrefix + prepareFallback;
        }
        n1ql.put("statement", prepareFallback);
        return n1ql;
