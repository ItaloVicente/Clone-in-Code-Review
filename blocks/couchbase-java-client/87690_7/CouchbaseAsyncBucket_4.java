        return n1qlQueryExecutor.execute(query, parent);
    }

    @Override
    public Observable<AsyncN1qlQueryResult> query(N1qlQuery query) {
        return query(query, null);
