    @Override
    public Observable<PreparedPayload> prepare(String statement) {
        return prepare(PrepareStatement.prepare(statement));
    }

    /**
     * {@inheritDoc}
     *
     * See also {@link QueryExecutor#prepare(Statement)}.
     */
    @Override
    public Observable<PreparedPayload> prepare(Statement statement) {
        return queryExecutor().prepare(statement);
    }
