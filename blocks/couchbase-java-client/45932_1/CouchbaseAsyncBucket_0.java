    @Override
    public Observable<QueryPlan> prepare(String statement) {
        String prepared = statement.toUpperCase().startsWith(PrepareStatement.PREPARE_PREFIX) ?
                statement : PrepareStatement.PREPARE_PREFIX + statement;
        return prepare(Query.simple(prepared));
    }

