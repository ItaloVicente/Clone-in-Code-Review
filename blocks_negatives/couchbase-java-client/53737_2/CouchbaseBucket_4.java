    @Override
    public PreparedPayload prepare(String statement) {
        return prepare(statement, environment.queryTimeout(), TIMEOUT_UNIT);
    }
    @Override
    public PreparedPayload prepare(Statement statement) {
        return prepare(statement, environment.queryTimeout(), TIMEOUT_UNIT);
    }
