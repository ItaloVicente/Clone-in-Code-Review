    public Observable<AsyncQueryResult> execute(final Query query) {
        if (query.params().isAdhoc()) {
            return executeQuery(query);
        } else {
            return dispatchPrepared(query);
        }
    }

    private Observable<AsyncQueryResult> dispatchPrepared(final Query query) {
        PreparedPayload payload = queryCache.get(query.statement().toString());

        if (payload != null) {
            return executePrepared(query, payload);
        } else {
            return prepare(query.statement())
                .flatMap(new Func1<PreparedPayload, Observable<AsyncQueryResult>>() {
                    @Override
                    public Observable<AsyncQueryResult> call(PreparedPayload payload) {
                        queryCache.put(query.statement().toString(), payload);
                        return executePrepared(query, payload);
                    }
                });
        }
    }

    private Observable<AsyncQueryResult> executePrepared(final Query query, PreparedPayload payload) {
        if (query instanceof ParameterizedQuery) {
            ParameterizedQuery pq = (ParameterizedQuery) query;
            if (pq.isPositional()) {
                return executeQuery(
                    Query.prepared(payload, (JsonArray) pq.statementParameters())
                );
            } else {
                return executeQuery(
                    Query.prepared(payload, (JsonObject) pq.statementParameters())
                );
            }
        } else {
            return executeQuery(Query.prepared(payload));
        }
    }

